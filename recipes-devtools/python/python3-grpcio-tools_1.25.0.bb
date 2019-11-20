DESCRIPTION = "grpcio-tools"
HOMEPAGE = "https://pypi.org/project/grpcio-tools/"
SECTION = "devel/python"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://MANIFEST.in;md5=4e1da04207560d860fc7e2eeb805727f"

DEPENDS += "python3 python3-cython"
RDEPENDS_${PN} = "python3-protobuf python3-six"

PR = "r0"

SRCNAME = "grpcio-tools"
SRC_URI = "https://files.pythonhosted.org/packages/e1/93/00edac4d6c8dc2e4c208f08a1317fbbd18ff766c9e71312473765a326a7a/${SRCNAME}-${PV}.tar.gz"
SRC_URI[md5sum] = "00813e18ece15b46f2df104e377343ee"
SRC_URI[sha256sum] = "988014c714ca654b3b7ca9f4dabfe487b00e023bfdd9eaf1bb0fed82bf8c4255"

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
