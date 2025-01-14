Changes
=======

5.2
---

 - #61: path.listdir now decodes filenames from os.listdir when loading
   characters from a file. On Python 3, the behavior is unchanged. On Python
   2, the behavior will now mimick that of Python 3, attempting to decode
   all filenames and paths using the encoding indicated by
   ``sys.getfilesystemencoding()``, and escaping any undecodable characters
   using the 'surrogateescape' handler.

5.1
---

 - #53: Added ``path.in_place`` for editing files in place.

5.0
---

 - ``path.fnmatch`` now takes an optional parameter ``normcase`` and this
   parameter defaults to self.module.normcase (using case normalization most
   pertinent to the path object itself). Note that this change means that
   any paths using a custom ntpath module on non-Windows systems will have
   different fnmatch behavior. Before::

       # on Unix
       >>> p = path('Foo')
       >>> p.module = ntpath
       >>> p.fnmatch('foo')
       False

   After::

       # on any OS
       >>> p = path('Foo')
       >>> p.module = ntpath
       >>> p.fnmatch('foo')
       True

   To maintain the original behavior, either don't define the 'module' for the
   path or supply explicit normcase function::

       >>> p.fnmatch('foo', normcase=os.path.normcase)
       # result always varies based on OS, same as fnmatch.fnmatch

   For most use-cases, the default behavior should remain the same.

 - Issue #50: Methods that accept patterns (``listdir``, ``files``, ``dirs``,
   ``walk``, ``walkdirs``, ``walkfiles``, and ``fnmatch``) will now use a
   ``normcase`` attribute if it is present on the ``pattern`` parameter. The
   path module now provides a ``CaseInsensitivePattern`` wrapper for strings
   suitable for creating case-insensitive patterns for those methods.

4.4
---

 - Issue #44: _hash method would open files in text mode, producing
   invalid results on Windows. Now files are opened in binary mode, producing
   consistent results.
 - Issue #47: Documentation is dramatically improved with Intersphinx links
   to the Python os.path functions and documentation for all methods and
   properties.

4.3
---

 - Issue #32: Add ``chdir`` and ``cd`` methods.

4.2
---

 - ``open()`` now passes all positional and keyword arguments through to the
   underlying ``builtins.open`` call.

4.1
---

 - Native Python 2 and Python 3 support without using 2to3 during the build
   process.

4.0
---

 - Added a ``chunks()`` method to a allow quick iteration over pieces of a
   file at a given path.
 - Issue #28: Fix missing argument to ``samefile``.
 - Initializer no longer enforces `isinstance basestring` for the source
   object. Now any object that supplies ``__unicode__`` can be used by a
   ``path`` (except None). Clients that depend on a ValueError being raised
   for ``int`` and other non-string objects should trap these types
   internally.
 - Issue #30: ``chown`` no longer requires both uid and gid to be provided
   and will not mutate the ownership if nothing is provided.

3.2
---

 - Issue #22: ``__enter__`` now returns self.

3.1
---

 - Issue #20: `relpath` now supports a "start" parameter to match the
   signature of `os.path.relpath`.

3.0
---

 - Minimum Python version is now 2.5.

2.6
---

 - Issue #5: Implemented `path.tempdir`, which returns a path object which is
   a temporary directory and context manager for cleaning up the directory.
 - Issue #12: One can now construct path objects from a list of strings by
   simply using path.joinpath. For example::

     path.joinpath('a', 'b', 'c') # or
     path.joinpath(*path_elements)

2.5
---

 - Issue #7: Add the ability to do chaining of operations that formerly only
   returned None.
 - Issue #4: Raise a TypeError when constructed from None.
