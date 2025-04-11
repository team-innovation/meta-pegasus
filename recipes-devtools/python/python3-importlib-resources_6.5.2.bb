SUMMARY = "Read resources from Python packages"
HOMEPAGE = "https://github.com/python/importlib_resources"
SECTION = "devel/python"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5="

SRC_URI[sha256sum] = ""

DEPENDS = "${PYTHON_PN}-setuptools-scm-native"

inherit python_setuptools_build_meta pypi