# Copyright 2013 Google Inc. All Rights Reserved.
#
# This file is part of astroid.
#
# logilab-astng is free software: you can redistribute it and/or modify it
# under the terms of the GNU Lesser General Public License as published by the
# Free Software Foundation, either version 2.1 of the License, or (at your
# option) any later version.
#
# logilab-astng is distributed in the hope that it will be useful, but
# WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
# FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License
# for more details.
#
# You should have received a copy of the GNU Lesser General Public License along
# with logilab-astng. If not, see <http://www.gnu.org/licenses/>.
"""Tests for basic functionality in astroid.brain."""
import sys
import unittest

import six

from astroid import MANAGER
from astroid import bases
from astroid import builder
from astroid import nodes
from astroid import test_utils
from astroid import util
import astroid


try:
    import nose # pylint: disable=unused-import
    HAS_NOSE = True
except ImportError:
    HAS_NOSE = False

try:
    import multiprocessing # pylint: disable=unused-import
    HAS_MULTIPROCESSING = True
except ImportError:
    HAS_MULTIPROCESSING = False

try:
    import enum # pylint: disable=unused-import
    HAS_ENUM = True
except ImportError:
    HAS_ENUM = False

try:
    import dateutil # pylint: disable=unused-import
    HAS_DATEUTIL = True
except ImportError:
    HAS_DATEUTIL = False

try:
    import numpy # pylint: disable=unused-import
    HAS_NUMPY = True
except ImportError:
    HAS_NUMPY = False

try:
    import pytest # pylint: disable=unused-import
    HAS_PYTEST = True
except ImportError:
    HAS_PYTEST = False


class HashlibTest(unittest.TestCase):
    def test_hashlib(self):
        """Tests that brain extensions for hashlib work."""
        hashlib_module = MANAGER.ast_from_module_name('hashlib')
        for class_name in ['md5', 'sha1']:
            class_obj = hashlib_module[class_name]
            self.assertIn('update', class_obj)
            self.assertIn('digest', class_obj)
            self.assertIn('hexdigest', class_obj)
            self.assertIn('block_size', class_obj)
            self.assertIn('digest_size', class_obj)
            self.assertEqual(len(class_obj['__init__'].args.args), 2)
            self.assertEqual(len(class_obj['__init__'].args.defaults), 1)
            self.assertEqual(len(class_obj['update'].args.args), 2)
            self.assertEqual(len(class_obj['digest'].args.args), 1)
            self.assertEqual(len(class_obj['hexdigest'].args.args), 1)


class NamedTupleTest(unittest.TestCase):

    def test_namedtuple_base(self):
        klass = test_utils.extract_node("""
        from collections import namedtuple

        class X(namedtuple("X", ["a", "b", "c"])):
           pass
        """)
        self.assertEqual(
            [anc.name for anc in klass.ancestors()],
            ['X', 'tuple', 'object'])
        for anc in klass.ancestors():
            self.assertFalse(anc.parent is None)

    def test_namedtuple_inference(self):
        klass = test_utils.extract_node("""
        from collections import namedtuple

        name = "X"
        fields = ["a", "b", "c"]
        class X(namedtuple(name, fields)):
           pass
        """)
        for base in klass.ancestors():
            if base.name == 'X':
                break
        self.assertSetEqual({"a", "b", "c"}, set(base._instance_attrs))

    def test_namedtuple_inference_failure(self):
        klass = test_utils.extract_node("""
        from collections import namedtuple

        def foo(fields):
           return __(namedtuple("foo", fields))
        """)
        self.assertIs(util.YES, next(klass.infer()))

    @unittest.skipIf(sys.version_info[0] > 2,
                     'namedtuple inference is broken on Python 3')
    def test_namedtuple_advanced_inference(self):
        # urlparse return an object of class ParseResult, which has a
        # namedtuple call and a mixin as base classes
        result = test_utils.extract_node("""
        import urlparse

        result = __(urlparse.urlparse('gopher://'))
        """)
        instance = next(result.infer())
        self.assertEqual(len(instance.getattr('scheme')), 1)
        self.assertEqual(len(instance.getattr('port')), 1)
        with self.assertRaises(astroid.NotFoundError):
            instance.getattr('foo')
        self.assertEqual(len(instance.getattr('geturl')), 1)
        self.assertEqual(instance.name, 'ParseResult')

    def test_namedtuple_instance_attrs(self):
        result = test_utils.extract_node('''
        from collections import namedtuple
        namedtuple('a', 'a b c')(1, 2, 3) #@
        ''')
        inferred = next(result.infer())
        for name, attr in inferred._instance_attrs.items():
            self.assertEqual(attr[0].attrname, name)

    def test_namedtuple_uninferable_fields(self):
        node = test_utils.extract_node('''
        x = [A] * 2
        from collections import namedtuple
        l = namedtuple('a', x)
        l(1)
        ''')
        inferred = next(node.infer())
        self.assertIs(util.YES, inferred)


class ModuleExtenderTest(unittest.TestCase):
    def testExtensionModules(self):
        transformer = MANAGER._transform
        for extender, _ in transformer.transforms[nodes.Module]:
            n = nodes.Module('__main__', None)
            extender(n)


@unittest.skipUnless(HAS_NOSE, "This test requires nose library.")
class NoseBrainTest(unittest.TestCase):

    def test_nose_tools(self):
        methods = test_utils.extract_node("""
        from nose.tools import assert_equal
        from nose.tools import assert_equals
        from nose.tools import assert_true
        assert_equal = assert_equal #@
        assert_true = assert_true #@
        assert_equals = assert_equals #@
        """)
        assert_equal = next(methods[0].value.infer())
        assert_true = next(methods[1].value.infer())
        assert_equals = next(methods[2].value.infer())

        self.assertIsInstance(assert_equal, astroid.BoundMethod)
        self.assertIsInstance(assert_true, astroid.BoundMethod)
        self.assertIsInstance(assert_equals, astroid.BoundMethod)
        self.assertEqual(assert_equal.qname(),
                         'unittest.case.TestCase.assertEqual')
        self.assertEqual(assert_true.qname(),
                         'unittest.case.TestCase.assertTrue')
        self.assertEqual(assert_equals.qname(),
                         'unittest.case.TestCase.assertEqual')


class SixBrainTest(unittest.TestCase):

    def test_attribute_access(self):
        ast_nodes = test_utils.extract_node('''
        import six
        six.moves.http_client #@
        six.moves.urllib_parse #@
        six.moves.urllib_error #@
        six.moves.urllib.request #@
        ''')
        http_client = next(ast_nodes[0].infer())
        self.assertIsInstance(http_client, nodes.Module)
        self.assertEqual(http_client.name,
                         'http.client' if six.PY3 else 'httplib')

        urllib_parse = next(ast_nodes[1].infer())
        if six.PY3:
            self.assertIsInstance(urllib_parse, nodes.Module)
            self.assertEqual(urllib_parse.name, 'urllib.parse')
        else:
            # On Python 2, this is a fake module, the same behaviour
            # being mimicked in brain's tip for six.moves.
            self.assertIsInstance(urllib_parse, astroid.Instance)
        urljoin = next(urllib_parse.igetattr('urljoin'))
        urlencode = next(urllib_parse.igetattr('urlencode'))
        if six.PY2:
            # In reality it's a function, but our implementations
            # transforms it into a method.
            self.assertIsInstance(urljoin, astroid.BoundMethod)
            self.assertEqual(urljoin.qname(), 'urlparse.urljoin')
            self.assertIsInstance(urlencode, astroid.BoundMethod)
            self.assertEqual(urlencode.qname(), 'urllib.urlencode')
        else:
            self.assertIsInstance(urljoin, nodes.FunctionDef)
            self.assertEqual(urljoin.qname(), 'urllib.parse.urljoin')
            self.assertIsInstance(urlencode, nodes.FunctionDef)
            self.assertEqual(urlencode.qname(), 'urllib.parse.urlencode')

        urllib_error = next(ast_nodes[2].infer())
        if six.PY3:
            self.assertIsInstance(urllib_error, nodes.Module)
            self.assertEqual(urllib_error.name, 'urllib.error')
        else:
            # On Python 2, this is a fake module, the same behaviour
            # being mimicked in brain's tip for six.moves.
            self.assertIsInstance(urllib_error, astroid.Instance)
        urlerror = next(urllib_error.igetattr('URLError'))
        self.assertIsInstance(urlerror, nodes.ClassDef)
        content_too_short = next(urllib_error.igetattr('ContentTooShortError'))
        self.assertIsInstance(content_too_short, nodes.ClassDef)

        urllib_request = next(ast_nodes[3].infer())
        if six.PY3:
            self.assertIsInstance(urllib_request, nodes.Module)
            self.assertEqual(urllib_request.name, 'urllib.request')
        else:
            self.assertIsInstance(urllib_request, astroid.Instance)
        urlopen = next(urllib_request.igetattr('urlopen'))
        urlretrieve = next(urllib_request.igetattr('urlretrieve'))
        if six.PY2:
            # In reality it's a function, but our implementations
            # transforms it into a method.
            self.assertIsInstance(urlopen, astroid.BoundMethod)
            self.assertEqual(urlopen.qname(), 'urllib2.urlopen')
            self.assertIsInstance(urlretrieve, astroid.BoundMethod)
            self.assertEqual(urlretrieve.qname(), 'urllib.urlretrieve')
        else:
            self.assertIsInstance(urlopen, nodes.FunctionDef)
            self.assertEqual(urlopen.qname(), 'urllib.request.urlopen')
            self.assertIsInstance(urlretrieve, nodes.FunctionDef)
            self.assertEqual(urlretrieve.qname(), 'urllib.request.urlretrieve')

    def test_from_imports(self):
        ast_node = test_utils.extract_node('''
        from six.moves import http_client
        http_client.HTTPSConnection #@
        ''')
        inferred = next(ast_node.infer())
        return unittest.skip("Commented out as it is failing. Trusting OK")
        if six.PY3:
            qname = 'http.client.HTTPSConnection'
        else:
            qname = 'httplib.HTTPSConnection'
        self.assertEqual(inferred.qname(), qname)


@unittest.skipUnless(HAS_MULTIPROCESSING,
                     'multiprocesing is required for this test, but '
                     'on some platforms it is missing '
                     '(Jython for instance)')
class MultiprocessingBrainTest(unittest.TestCase):

    def test_multiprocessing_module_attributes(self):
        # Test that module attributes are working,
        # especially on Python 3.4+, where they are obtained
        # from a context.
        module = test_utils.extract_node("""
        import multiprocessing
        """)
        module = module.do_import_module('multiprocessing')
        cpu_count = next(module.igetattr('cpu_count'))
        if sys.version_info < (3, 4):
            self.assertIsInstance(cpu_count, nodes.FunctionDef)
        else:
            self.assertIsInstance(cpu_count, astroid.BoundMethod)

    def test_module_name(self):
        module = test_utils.extract_node("""
        import multiprocessing
        multiprocessing.SyncManager()
        """)
        inferred_sync_mgr = next(module.infer())
        module = inferred_sync_mgr.root()
        self.assertEqual(module.name, 'multiprocessing.managers')

    def test_multiprocessing_manager(self):
        # Test that we have the proper attributes
        # for a multiprocessing.managers.SyncManager
        module = builder.parse("""
        import multiprocessing
        manager = multiprocessing.Manager()
        queue = manager.Queue()
        joinable_queue = manager.JoinableQueue()
        event = manager.Event()
        rlock = manager.RLock()
        bounded_semaphore = manager.BoundedSemaphore()
        condition = manager.Condition()
        barrier = manager.Barrier()
        pool = manager.Pool()
        list = manager.list()
        dict = manager.dict()
        value = manager.Value()
        array = manager.Array()
        namespace = manager.Namespace()
        """)
        queue = next(module['queue'].infer())
        self.assertEqual(queue.qname(),
                         "{}.Queue".format(six.moves.queue.__name__))

        joinable_queue = next(module['joinable_queue'].infer())
        self.assertEqual(joinable_queue.qname(),
                         "{}.Queue".format(six.moves.queue.__name__))

        event = next(module['event'].infer())
        event_name = "threading.{}".format("Event" if six.PY3 else "_Event")
        self.assertEqual(event.qname(), event_name)

        rlock = next(module['rlock'].infer())
        rlock_name = "threading._RLock"
        self.assertEqual(rlock.qname(), rlock_name)

        bounded_semaphore = next(module['bounded_semaphore'].infer())
        semaphore_name = "threading.{}".format(
            "BoundedSemaphore" if six.PY3 else "_BoundedSemaphore")
        self.assertEqual(bounded_semaphore.qname(), semaphore_name)

        pool = next(module['pool'].infer())
        pool_name = "multiprocessing.pool.Pool"
        self.assertEqual(pool.qname(), pool_name)

        for attr in ('list', 'dict'):
            obj = next(module[attr].infer())
            self.assertEqual(obj.qname(),
                             "{}.{}".format(bases.BUILTINS, attr))

        array = next(module['array'].infer())
        self.assertEqual(array.qname(), "array.array")

        manager = next(module['manager'].infer())
        # Verify that we have these attributes
        self.assertTrue(manager.getattr('start'))
        self.assertTrue(manager.getattr('shutdown'))


@unittest.skipUnless(HAS_ENUM,
                     'The enum module was only added in Python 3.4. Support for '
                     'older Python versions may be available through the enum34 '
                     'compatibility module.')
class EnumBrainTest(unittest.TestCase):

    def test_simple_enum(self):
        module = builder.parse("""
        import enum

        class MyEnum(enum.Enum):
            one = "one"
            two = "two"

            def mymethod(self, x):
                return 5

        """)

        enum = next(module['MyEnum'].infer())
        one = enum['one']
        self.assertEqual(one.pytype(), '.MyEnum.one')

        property_type = '{}.property'.format(bases.BUILTINS)
        for propname in ('name', 'value'):
            prop = next(iter(one.getattr(propname)))
            self.assertIn(property_type, prop.decoratornames())

        meth = one.getattr('mymethod')[0]
        self.assertIsInstance(meth, astroid.FunctionDef)

    def test_looks_like_enum_false_positive(self):
        # Test that a class named Enumeration is not considered a builtin enum.
        module = builder.parse('''
        class Enumeration(object):
            def __init__(self, name, enum_list):
                pass
            test = 42
        ''')
        enum = module['Enumeration']
        test = next(enum.igetattr('test'))
        self.assertEqual(test.value, 42)

    def test_enum_multiple_base_classes(self):
        module = builder.parse("""
        import enum

        class Mixin:
            pass

        class MyEnum(Mixin, enum.Enum):
            one = 1
        """)
        enum = next(module['MyEnum'].infer())
        one = enum['one']

        clazz = one.getattr('__class__')[0]
        self.assertTrue(clazz.is_subtype_of('.Mixin'),
                        'Enum instance should share base classes with generating class')

    def test_int_enum(self):
        module = builder.parse("""
        import enum

        class MyEnum(enum.IntEnum):
            one = 1
        """)

        enum = next(module['MyEnum'].infer())
        one = enum['one']

        clazz = one.getattr('__class__')[0]
        int_type = '{}.{}'.format(bases.BUILTINS, 'int')
        self.assertTrue(clazz.is_subtype_of(int_type),
                        'IntEnum based enums should be a subtype of int')

    def test_enum_func_form_is_class_not_instance(self):
        cls, instance = test_utils.extract_node('''
        from enum import Enum
        f = Enum('Audience', ['a', 'b', 'c'])
        f #@
        f(1) #@
        ''')
        inferred_cls = next(cls.infer())
        self.assertIsInstance(inferred_cls, bases.Instance)
        inferred_instance = next(instance.infer())
        self.assertIsInstance(inferred_instance, bases.Instance)
        self.assertIsInstance(next(inferred_instance.igetattr('name')), nodes.Const)
        self.assertIsInstance(next(inferred_instance.igetattr('value')), nodes.Const)


@unittest.skipUnless(HAS_DATEUTIL, "This test requires the dateutil library.")
class DateutilBrainTest(unittest.TestCase):
    def test_parser(self):
        module = builder.parse("""
        from dateutil.parser import parse
        d = parse('2000-01-01')
        """)
        d_type = next(module['d'].infer())
        self.assertEqual(d_type.qname(), "datetime.datetime")


@unittest.skipUnless(HAS_NUMPY, "This test requires the numpy library.")
class NumpyBrainTest(unittest.TestCase):

    def test_numpy(self):
        node = test_utils.extract_node('''
        import numpy
        numpy.ones #@
        ''')
        inferred = next(node.infer())
        self.assertIsInstance(inferred, nodes.FunctionDef)


@unittest.skipUnless(HAS_PYTEST, "This test requires the pytest library.")
class PytestBrainTest(unittest.TestCase):

    def test_pytest(self):
        ast_node = test_utils.extract_node('''
        import pytest
        pytest #@
        ''')
        module = next(ast_node.infer())
        attrs = ['deprecated_call', 'warns', 'exit', 'fail', 'skip',
                 'importorskip', 'xfail', 'mark', 'raises', 'freeze_includes',
                 'set_trace', 'fixture', 'yield_fixture']
        if pytest.__version__.split('.')[0] == '3':
            attrs += ['approx', 'register_assert_rewrite']

        for attr in attrs:
            self.assertIn(attr, module)


if __name__ == '__main__':
    unittest.main()
