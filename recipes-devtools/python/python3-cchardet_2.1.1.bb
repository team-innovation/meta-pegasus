DESCRIPTION = "cChardet is high speed universal character encoding detector."
HOMEPAGE = "https://pypi.org/project/cchardet/"
SECTION = "devel/python"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://COPYING;md5=6ecda54f6f525388d71d6b3cd92f7474"

DEPENDS += "python3"

PR = "r1"
SRCNAME = "cchardet"

SRC_URI = "https://files.pythonhosted.org/packages/63/74/fbf92cd7fe2e603600096098d78f5c5957c5071861298d00084f058e174f/cchardet-2.1.1.tar.gz"


S = "${WORKDIR}/${SRCNAME}-${PV}"

SRC_URI[md5sum] = "bbfb26239b5129e93c8812efcc54d935"
SRC_URI[sha256sum] = "9c9269208b9f8d7446dbd970f6544ce48104096efab0f769ee5918066ba1ee7e"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native"

NATIVE_INSTALL_WORKS = "1"

do_install_prepend() {
    install -d ${D}/${libdir}/${PYTHON_DIR}/site-packages
}

do_package_qa() {
    echo "Skipping QA ..."
}

inherit setuptools3 python3-dir
