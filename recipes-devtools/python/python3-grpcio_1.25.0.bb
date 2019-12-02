DESCRIPTION = "grpcio"
HOMEPAGE = "https://pypi.python.org/pypi/grpcio"
SECTION = "devel/python"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"

DEPENDS += "python3 python3-cython"
RDEPENDS_${PN} = "python3-protobuf python3-six"

PR = "r3"

SRCNAME = "grpcio"
SRC_URI = "https://files.pythonhosted.org/packages/e4/60/40c4d2b61d9e4349bc89445deb8d04cc000b10a63446c42d311e0d21d127/${SRCNAME}-${PV}.tar.gz"
SRC_URI += "file://disable-grpc-forking.patch"

SRC_URI[md5sum] = "14c23bbb1db72f6b8fe262b549203eb3"
SRC_URI[sha256sum] = "c948c034d8997526011960db54f512756fb0b4be1b81140a15b4ef094c6594a4"

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
