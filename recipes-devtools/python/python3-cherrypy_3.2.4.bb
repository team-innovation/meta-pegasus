DESCRIPTION = "Python CherryPy"
SECTION = "devel/python"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://cherrypy/LICENSE.txt;md5=c187ff3653a0878075713adef2c545c3"
PR = "ml0"

DEPENDS += "python3"
DEPENDS_virtclass-native += "python3-native"
RDEPENDS_${PN} = "python3-core"

inherit setuptools3 python3-dir

SRC_URI = "https://pypi.python.org/packages/source/C/CherryPy/CherryPy-3.2.4.tar.gz"

S = "${WORKDIR}/CherryPy-${PV}"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native"

NATIVE_INSTALL_WORKS = "1"


do_install_prepend() {
    install -d ${D}/${libdir}/${PYTHON_DIR}/site-packages
}

# removed tutorial and test
do_install_append() {
   rm -rf ${D}/${libdir}/${PYTHON_DIR}/site-packages/cherrypy/tutorial
   rm -rf ${D}/${libdir}/${PYTHON_DIR}/site-packages/cherrypy/test
}

SRC_URI[md5sum] = "e2c8455e15c39c9d60e0393c264a4d16"
SRC_URI[sha256sum] = "abd73a449936740e99d3a05eb89b9381dc188ef696904f585463bc28079f1288"

INSANE_SKIP_${PN} = "installed-vs-shipped"
