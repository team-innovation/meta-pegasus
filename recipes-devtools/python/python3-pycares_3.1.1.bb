SUMMARY = "Python interface for c-ares"
HOMEPAGE = "https://github.com/saghul/pycares"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=b1538fcaea82ebf2313ed648b96c69b1"

DEPENDS += "${PYTHON_PN}-cffi-native"

SRC_URI[md5sum] = "8ac802d79b318efa27d3a9949d0604d1"
SRC_URI[sha256sum] = "18dfd4fd300f570d6c4536c1d987b7b7673b2a9d14346592c5d6ed716df0d104"

PYPI_PACKAGE = "pycares"
inherit pypi setuptools3 

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native nativesdk"

