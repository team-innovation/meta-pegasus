SUMMARY = "Read resources from Python packages"
HOMEPAGE = "https://github.com/python/importlib_resources"
SECTION = "devel/python"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5="

SRC_URI = "https://files.pythonhosted.org/packages/cf/8c/f834fbf984f691b4f7ff60f50b514cc3de5cc08abfc3295564dd89c5e2e7/importlib_resources-${PV}.tar.gz"
SRC_URI[sha256sum] = ""

DEPENDS = "${PYTHON_PN}-setuptools-scm-native"

inherit python_setuptools_build_meta pypi