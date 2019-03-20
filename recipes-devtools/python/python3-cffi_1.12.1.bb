DESCRIPTION = "cffi"
HOMEPAGE = "https://pypi.org/project/cffi"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=5677e2fdbf7cdda61d6dd2b57df547bf"

DEPENDS += "python3 python3-pycparser libffi"
#RDEPENDS_${PN} = ""

PR = "r1"

SRCNAME = "cffi"
SRC_URI = "https://files.pythonhosted.org/packages/bc/81/47bd0404f2cb5363edb371e3b15da6387b5e9b80122e5b81be8b8f411e9b/${SRCNAME}-${PV}.tar.gz"

SRC_URI[md5sum] = "d6d5c4805bbce844cf1368702b056e3c"
SRC_URI[sha256sum] = "9b6f7ba4e78c52c1a291d0c0c0bd745d19adde1a9e1c03cb899f0c6efd6f8033"

S = "${WORKDIR}/${SRCNAME}-${PV}"

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
