DESCRIPTION = "Python mock"
SECTION = "devel/python"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=fa262dc398460c9421d380ba291a5763"
PR = "ml0"

DEPENDS += "python3"

SRC_URI = "http://www.voidspace.org.uk/downloads/mock-0.8.0beta4.tar.gz"

S = "${WORKDIR}/mock-${PV}"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native"

NATIVE_INSTALL_WORKS = "1"

inherit setuptools3 python3-dir

do_install_prepend() {
    install -d ${D}/${libdir}/${PYTHON_DIR}/site-packages
}

SRC_URI[md5sum] = "cdb6debd823a73d9636b28a5a28396e7"
SRC_URI[sha256sum] = "6dc5c46e141493c75d093bbf6dd6d0f6d4f96aaa522b9f906b61fbaf56a54602"
