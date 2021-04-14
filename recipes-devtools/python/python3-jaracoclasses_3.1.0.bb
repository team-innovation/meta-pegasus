SUMMARY = "Python jaraco classes."
DESCRIPTION = "Utility functions for Python class constructs"
HOMEPAGE = "https://github.com/jaraco/jaraco.classes"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=a33f38bbf47d48c70fe0d40e5f77498e"

DEPENDS += "${PYTHON_PN}-setuptools-scm-native"

SRC_URI[md5sum] = "d19a8131c4f501dba9dbd904b366020b"
SRC_URI[sha256sum] = "c38698ff8ef932eb33d91c0e8fc192ad7c44ecee03f7f585afd4f35aeaef7aab"

PYPI_PACKAGE = "jaraco.classes"
inherit setuptools3 pypi

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native nativesdk"
