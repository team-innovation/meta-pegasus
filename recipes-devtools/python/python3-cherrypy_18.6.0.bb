DESCRIPTION = "Python CherryPy"
SECTION = "devel/python"
HOMEPAGE = "https://cherrypy.org/"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=a8cbc5da4e6892b15a972a0b18622b2b"

DEPENDS += "${PYTHON_PN}-setuptools-scm-native"

SRC_URI[md5sum] = "cf3f9b9de7a504febe1bfbd43797b428"
SRC_URI[sha256sum] = "56608edd831ad00991ae585625e0206ed61cf1a0850e4b2cc48489fb2308c499"

PYPI_PACKAGE = "CherryPy"
inherit setuptools3 pypi

# removed tutorial and test
do_install_append() {
   rm -rf ${D}/${libdir}/${PYTHON_DIR}/site-packages/cherrypy/tutorial
   rm -rf ${D}/${libdir}/${PYTHON_DIR}/site-packages/cherrypy/test
}

RDEPENDS_${PN} = " \
    ${PYTHON_PN}-cheroot \
    ${PYTHON_PN}-portend \
    ${PYTHON_PN}-more-itertools \
    ${PYTHON_PN}-zclockfile \
    ${PYTHON_PN}-jaracocollections \
"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native nativesdk"
