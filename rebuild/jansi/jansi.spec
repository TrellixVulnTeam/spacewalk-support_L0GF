Name:             jansi
Version:          1.12
Release:          1%{?dist}
Summary:          Jansi is a java library for generating and interpreting ANSI escape sequences
License:          ASL 2.0
URL:              http://fusesource.github.io/jansi/

Source0:          https://github.com/fusesource/jansi/archive/jansi-project-%{version}.tar.gz

BuildArch:        noarch

BuildRequires:    maven-local
BuildRequires:    mvn(junit:junit)
BuildRequires:    mvn(org.apache.felix:maven-bundle-plugin)
BuildRequires:    mvn(org.fusesource:fusesource-pom:pom:)
BuildRequires:    mvn(org.fusesource.hawtjni:hawtjni-runtime)
BuildRequires:    mvn(org.fusesource.jansi:jansi-native)

%description
Jansi is a small java library that allows you to use ANSI escape sequences
in your Java console applications. It implements ANSI support on platforms
which don't support it like Windows and provides graceful degradation for
when output is being sent to output devices which cannot support ANSI sequences.

%package javadoc
Summary:          Javadocs for %{name}

%description javadoc
This package contains the API documentation for %{name}.

%prep
%setup -q -n jansi-jansi-project-%{version}

#%pom_disable_module example
%pom_xpath_remove "pom:build/pom:extensions"

%pom_remove_plugin -r :maven-site-plugin

# No maven-uberize-plugin
%pom_remove_plugin -r :maven-uberize-plugin

# Remove unnecessary deps for jansi-native builds
pushd jansi
%pom_remove_dep :jansi-windows32
%pom_remove_dep :jansi-windows64
%pom_remove_dep :jansi-osx
#%pom_remove_dep :jansi-freebsd32
#%pom_remove_dep :jansi-freebsd64
# it's there only to be bundled in uberjar and we disable uberjar generation
%pom_remove_dep :jansi-linux32
%pom_remove_dep :jansi-linux64
popd

# javadoc generation fails due to strict doclint in JDK 8
%pom_remove_plugin -r :maven-javadoc-plugin

%build
%define xmvn_bootstrap true
%mvn_build 

%install
%mvn_install

%files -f .mfiles
%license license.txt
%doc readme.md changelog.md

%files javadoc -f .mfiles-javadoc
%license license.txt

%changelog
* Thu Mar 12 2020 Stefan Bluhm <stefan.bluhm@clacee.eu> - 1.12-1
- Update to upstream version 1.12

* Fri Feb 10 2017 Fedora Release Engineering <releng@fedoraproject.org> - 1.11-12
- Rebuilt for https://fedoraproject.org/wiki/Fedora_26_Mass_Rebuild

* Wed Feb 01 2017 Michael Simacek <msimacek@redhat.com> - 1.11-11
- Remove BR on maven-site-plugin

* Thu Feb 04 2016 Fedora Release Engineering <releng@fedoraproject.org> - 1.11-10
- Rebuilt for https://fedoraproject.org/wiki/Fedora_24_Mass_Rebuild

* Wed Jun 17 2015 Fedora Release Engineering <rel-eng@lists.fedoraproject.org> - 1.11-9
- Rebuilt for https://fedoraproject.org/wiki/Fedora_23_Mass_Rebuild

* Thu May 14 2015 Mikolaj Izdebski <mizdebsk@redhat.com> - 1.11-8
- Remove maven-javadoc-plugin execution

* Tue Jan 27 2015 Mat Booth <mat.booth@redhat.com> - 1.11-7
- Add/remove BRs to fix FTBFS bug

* Sat Jun 07 2014 Fedora Release Engineering <rel-eng@lists.fedoraproject.org> - 1.11-6
- Rebuilt for https://fedoraproject.org/wiki/Fedora_21_Mass_Rebuild

* Mon May 26 2014 Mikolaj Izdebski <mizdebsk@redhat.com> - 1.11-5
- Migrate BuildRequires from junit4 to junit

* Mon May 26 2014 Mikolaj Izdebski <mizdebsk@redhat.com> - 1.11-4
- Remove BuildRequires on maven-surefire-provider-junit4

* Wed Sep 11 2013 Marek Goldmann <mgoldman@redhat.com> - 1.11-3
- Using xmvn
- Remove the jboss-native deps with classifiers

* Sat Aug 03 2013 Fedora Release Engineering <rel-eng@lists.fedoraproject.org> - 1.11-2
- Rebuilt for https://fedoraproject.org/wiki/Fedora_20_Mass_Rebuild

* Tue May 21 2013 Marek Goldmann <mgoldman@redhat.com> - 1.11-1
- Upstream release 1.11 RHBZ#962761
- CVE-2013-2035 HawtJNI: predictable temporary file name leading to local arbitrary code execution RHBZ#962614

* Thu Feb 14 2013 Fedora Release Engineering <rel-eng@lists.fedoraproject.org> - 1.9-3
- Rebuilt for https://fedoraproject.org/wiki/Fedora_19_Mass_Rebuild

* Wed Feb 06 2013 Java SIG <java-devel@lists.fedoraproject.org> - 1.9-2
- Update for https://fedoraproject.org/wiki/Fedora_19_Maven_Rebuild
- Replace maven BuildRequires with maven-local

* Tue Oct 09 2012 Marek Goldmann <mgoldman@redhat.com> - 1.9-1
- Upstream release 1.9, RHBZ#864490

* Thu Jul 19 2012 Fedora Release Engineering <rel-eng@lists.fedoraproject.org> - 1.6-4
- Rebuilt for https://fedoraproject.org/wiki/Fedora_18_Mass_Rebuild

* Fri May 04 2012 Tomas Radej <tradej@redhat.com> - 1.6-3
- Removed maven-license-plugin BR

* Fri Jan 13 2012 Fedora Release Engineering <rel-eng@lists.fedoraproject.org> - 1.6-2
- Rebuilt for https://fedoraproject.org/wiki/Fedora_17_Mass_Rebuild

* Thu Aug 25 2011 Marek Goldmann <mgoldman@redhat.com> 1.6-1
- Upstream release 1.6
- Spec file cleanup

* Fri May 27 2011 Marek Goldmann <mgoldman@redhat.com> 1.5-1
- Initial packaging

