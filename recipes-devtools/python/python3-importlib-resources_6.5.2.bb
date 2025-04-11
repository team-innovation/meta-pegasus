SUMMARY = "Read resources from Python packages"
HOMEPAGE = "https://github.com/python/importlib_resources"
SECTION = "devel/python"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"

PYPI_PACKAGE = "importlib_resources"

SRC_URI[sha256sum] = "185f87adef5bcc288449d98fb4fba07cea78bc036455dd44c5fc4a2fe78fed2c"

DEPENDS = "${PYTHON_PN}-setuptools-scm-native"

inherit python_setuptools_build_meta pypi