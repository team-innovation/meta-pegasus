SUMMARY = "Object-Oriented HTTP framework"
HOMEPAGE = "https://github.com/cherrypy/cherrypy"
SECTION = "devel/python"

LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=a5ad8f932e1fd3841133f20d3ffedda1"

SRC_URI[sha256sum] = "6c70e78ee11300e8b21c0767c542ae6b102a49cac5cfd4e3e313d7bb907c5891"

DEPENDS = "${PYTHON_PN}-setuptools-scm-native"

inherit python_setuptools_build_meta pypi