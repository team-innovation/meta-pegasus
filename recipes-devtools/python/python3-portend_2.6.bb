SUMMARY = "Python portend TCP monitoring"
HOMEPAGE = "https://github.com/jaraco/portend"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=a33f38bbf47d48c70fe0d40e5f77498e"

DEPENDS += "${PYTHON_PN}-setuptools-scm-native"

SRC_URI[md5sum] = "f0c866649b4b9758f75ef6641907bfe7"
SRC_URI[sha256sum] = "600dd54175e17e9347e5f3d4217aa8bcf4bf4fa5ffbc4df034e5ec1ba7cdaff5"

PYPI_PACKAGE = "portend"
inherit setuptools3 pypi

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native nativesdk"
