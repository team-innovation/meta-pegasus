DESCRIPTION = "Python CherryPy"
SECTION = "devel/python"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://cherrypy/LICENSE.txt;md5=c187ff3653a0878075713adef2c545c3"
PR = "ml0"

DEPENDS += "python3"
RDEPENDS_${PN} = "python3-core"


PYPI_PACKAGE = "CherryPy"
inherit setuptools3 pypi

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native nativesdk"

# removed tutorial and test
do_install_append() {
   rm -rf ${D}/${libdir}/${PYTHON_DIR}/site-packages/cherrypy/tutorial
   rm -rf ${D}/${libdir}/${PYTHON_DIR}/site-packages/cherrypy/test
}


FILES_${PN} += "/usr/lib/* /usr/share/cherrypy"

SRC_URI[md5sum] = "e2c8455e15c39c9d60e0393c264a4d16"
SRC_URI[sha256sum] = "abd73a449936740e99d3a05eb89b9381dc188ef696904f585463bc28079f1288"

