SUMMARY = "Python bson"
DESCRIPTION = "Python bson codec that doesn't depend on MongoDB"
HOMEPAGE = "https://github.com/py-bson/bson"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://README.rst;md5=c1042b7bd704f64fe194d5bc5da9a359"

SRC_URI[md5sum] = "3c6b68f6aa61842031a5fac9c7bb3489"
SRC_URI[sha256sum] = "d6511b2ab051139a9123c184de1a04227262173ad593429d21e443d6462d6590"

PYPI_PACKAGE = "bson"
inherit setuptools3 pypi

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native nativesdk"
