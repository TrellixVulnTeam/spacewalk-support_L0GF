echo "Usage: $0 specfile tag build version"
SPECFILE=`realpath -e $1`
export SHORTNAME=`sed -n 's/^Name://p' $1 | sed 's/[ \t]//g'`
export VERSION=`sed -n 's/^Version://p' $1 | sed 's/[ \t]//g'`
export RELEASE=`sed -n 's/^Release://p' $1 | sed 's/[ \t]//g' |  sed 's/%.*//'`

echo "Processing tag ${SHORTNAME}-${VERSION}-${RELEASE}"

TAG=false
BUILD=false

if [ "$2" = "tag" ] || [ "$3" = "tag" ]
then
        TAG=true
fi

if [ "$2" = "build" ] || [ "$3" = "build" ]
then
        BUILD=true
fi

export LOCATION=`dirname $SPECFILE`
export SHORTNAME=`basename $LOCATION`
if $BUILD
then
	rm -Rf ${LOCATION}-${VERSION}
	cp -R ${LOCATION} ${LOCATION}-${VERSION}
	rm -f ~/rpmbuild/SOURCES/${SHORTNAME}-${VERSION}.tar.gz;
	cd ${LOCATION}
        cd ..
	tar cf ~/rpmbuild/SOURCES/${SHORTNAME}-${VERSION}.tar.gz ${SHORTNAME}-${VERSION}
	rm -Rf ${LOCATION}-${VERSION}
	rpmbuild -ba $SPECFILE
#cd ~/spacewalk
fi

if $TAG
then
        git tag -d ${SHORTNAME}-${VERSION}-${RELEASE} &> /dev/null
        git push --delete origin ${SHORTNAME}-${VERSION}-${RELEASE} &> /dev/null
        git tag ${SHORTNAME}-${VERSION}-${RELEASE}
        git push origin --tags
fi
