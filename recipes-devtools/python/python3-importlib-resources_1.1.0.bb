SUMMARY = "Python importlib classes."
DESCRIPTION = "Backport of python 3.9 importlib resources"
HOMEPAGE = "https://github.com/python/importlib_resources"
SECTION = "devel/python"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=e81780ac4c0888aaef94a7cb49b55edc"

SRC_URI[md5sum] = "83adc670558175f0eceb4ad31c290dff"
SRC_URI[sha256sum] = "44bbe129a4ff27fcc0bae81f10f411bb011015b9afb1f0dde6234724d96966ae"

DEPENDS += "${PYTHON_PN}-setuptools-scm-native"

RDEPENDS_${PN} += "${PYTHON_PN}-zipp"

PYPI_PACKAGE = "importlib_resources"
inherit setuptools3 pypi

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native nativesdk"
