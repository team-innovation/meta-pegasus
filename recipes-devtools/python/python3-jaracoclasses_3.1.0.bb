SUMMARY = "Python collections."
DESCRIPTION = "Python Models and classes to supplement the stdlib ‘collections’ module."
HOMEPAGE = "https://github.com/jaraco/jaraco.classes"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=a33f38bbf47d48c70fe0d40e5f77498e"

DEPENDS += "${PYTHON_PN}-setuptools-scm-native"

SRC_URI[md5sum] = "d19a8131c4f501dba9dbd904b366020b"
SRC_URI[sha256sum] = "c38698ff8ef932eb33d91c0e8fc192ad7c44ecee03f7f585afd4f35aeaef7aab"

PYPI_PACKAGE = "jaraco.classes"
inherit setuptools3 pypi

RDEPENDS_${PN} = " \
	${PYTHON_PN}-more-itertools \
"

do_install_append() {
	rm ${D}${PYTHON_SITEPACKAGES_DIR}/jaraco/__init__.py
	rm ${D}${PYTHON_SITEPACKAGES_DIR}/jaraco/__pycache__/__init__.cpython-37.pyc
}

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native nativesdk"
