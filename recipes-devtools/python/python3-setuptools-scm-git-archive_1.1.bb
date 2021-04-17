SUMMARY = "Python Cheroot"
HOMEPAGE = "https://github.com/Changaco/setuptools_scm_git_archive/"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=838c366f69b72c5df05c96dff79b35f2"

DEPENDS += "${PYTHON_PN}-setuptools-scm-native"

SRC_URI[md5sum] = "1c9351fa5cebd12e76488737a7c78f2e"
SRC_URI[sha256sum] = "6026f61089b73fa1b5ee737e95314f41cb512609b393530385ed281d0b46c062"

PYPI_PACKAGE = "setuptools_scm_git_archive"
inherit setuptools3 pypi

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native nativesdk"
