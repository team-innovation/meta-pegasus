DESCRIPTION = "Classes Without Boilerplate"
HOMEPAGE = "https://pypi.org/project/attrs/"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=d4ab25949a73fe7d4fdee93bcbdbf8ff"

DEPENDS += "python3"

PR = "r1"
SRCNAME = "attrs"

SRC_URI = "https://files.pythonhosted.org/packages/e4/ac/a04671e118b57bee87dabca1e0f2d3bda816b7a551036012d0ca24190e71/attrs-18.1.0.tar.gz"


S = "${WORKDIR}/${SRCNAME}-${PV}"
SRC_URI[md5sum] = "3f3f3e0750dab74cfa1dc8b0fd7a5f86"
SRC_URI[sha256sum] = "e0d0eb91441a3b53dab4d9b743eafc1ac44476296a2053b6ca3af0b139faf87b"

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
