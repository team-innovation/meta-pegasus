SUMMARY = "Python collections."
DESCRIPTION = "Python Models and classes to supplement the stdlib ‘collections’ module."
HOMEPAGE = "https://github.com/jaraco/jaraco.collections"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=a33f38bbf47d48c70fe0d40e5f77498e"

DEPENDS += "${PYTHON_PN}-setuptools-scm-native"

SRC_URI[md5sum] = "2be29c314e91d144ac9ba85904afb8f5"
SRC_URI[sha256sum] = "be570ef4f2e7290b757449395238fa63d70a9255574624e73c5ff9f1ee554721"

PYPI_PACKAGE = "jaraco.collections"
inherit setuptools3 pypi

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native nativesdk"
