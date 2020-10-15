SUMMARY = "Python collections."
DESCRIPTION = "Python Models and classes to supplement the stdlib ‘collections’ module."
HOMEPAGE = "https://github.com/jaraco/jaraco.classes"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=a33f38bbf47d48c70fe0d40e5f77498e"

DEPENDS += "${PYTHON_PN}-setuptools-scm-native"

SRC_URI[md5sum] = "8085591783313b02afa591209b6e6ef5"
SRC_URI[sha256sum] = "e5078b1126cc0f166c7859aa75103a56c0d0f39ebcafc21695615472e0f810ec"

PYPI_PACKAGE = "jaraco.text"
inherit setuptools3 pypi

RDEPENDS_${PN} = " \
	${PYTHON_PN}-jaracofunctools \
"

do_install_append() {
	rm ${D}${PYTHON_SITEPACKAGES_DIR}/jaraco/__init__.py
	rm ${D}${PYTHON_SITEPACKAGES_DIR}/jaraco/__pycache__/__init__.cpython-37.pyc
}

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native nativesdk"
