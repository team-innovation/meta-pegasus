DESCRIPTION = "grpcio"
HOMEPAGE = "https://pypi.python.org/pypi/grpcio"
SECTION = "devel/python"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://README.md;md5=a4d64601c5848fb026bafd13200f2365"

DEPENDS += "python3"
DEPENDS_virtclass-native += "python3-native"

PR = "r1"
SRCNAME = "grpcio"

SRC_URI = "https://pypi.python.org/packages/ed/e8/be7f191d215b0dd5f6a321569592e6fdd54fdf2db0cc48cd0df2fd067bb8/grpcio-1.8.4.tar.gz \
           "

SRC_URI[md5sum] = "7860f7c61de3890323670b7b1ff63e56"
SRC_URI[sha256sum] = "88d87aab9c7889b3ab29dd74aac1a5493ed78b9bf5afba1c069c9dd5531f951d"

RDEPENDS_${PN} =" python3-protobuf \
                  python3-setuptools \
                  python3-six \
"

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
