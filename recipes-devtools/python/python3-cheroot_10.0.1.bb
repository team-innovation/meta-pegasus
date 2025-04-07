SUMMARY = "Highly-optimized, pure-python HTTP server"
HOMEPAGE = "https://pypi.org/project/cheroot"
SECTION = "devel/python"

LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=c4e17b64eab9c128f786f44f0dfb570a"

SRC_URI[sha256sum] = "e0b82f797658d26b8613ec8eb563c3b08e6bd6a7921e9d5089bd1175ad1b1740"

DEPENDS += "${PYTHON_PN}-setuptools-scm-git-archive-native"

inherit python_setuptools_build_meta pypi