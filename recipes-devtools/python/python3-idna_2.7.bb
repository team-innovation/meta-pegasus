DESCRIPTION = "Internationalized Domain Names in Applications (IDNA)"
HOMEPAGE = "https://pypi.org/project/idna/"
SECTION = "devel/python"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE.rst;md5=782775b32f96098512e283fb5d4546cd"

DEPENDS += "python3"

PR = "r1"
SRCNAME = "idna"

SRC_URI = "https://files.pythonhosted.org/packages/65/c4/80f97e9c9628f3cac9b98bfca0402ede54e0563b56482e3e6e45c43c4935/idna-2.7.tar.gz"


S = "${WORKDIR}/${SRCNAME}-${PV}"

SRC_URI[md5sum] = "0e5bb69018ddef1b9d95f681182be82c"
SRC_URI[sha256sum] = "684a38a6f903c1d71d6d5fac066b58d7768af4de2b832e426ec79c30daa94a16"

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
