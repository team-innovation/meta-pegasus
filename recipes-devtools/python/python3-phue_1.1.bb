SUMMARY = "Python interface to Phillips Hue bridge"
HOMEPAGE = "https://github.com/studioimaginaire/phue"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://PKG-INFO;md5=e9311e91988724ae3adcad53bedeed25"

SRC_URI[md5sum] = "e4954c8337eccb8f92c3ae49cdfa3209"
SRC_URI[sha256sum] = "61facc991a2ead1d727df8513d06ce089033489b6dca09be093db24f7cdb8874"

PYPI_PACKAGE = "phue"
inherit setuptools3 pypi

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native nativesdk"
