DESCRIPTION = "cryptography"
HOMEPAGE = "https://pypi.org/project/cryptography"
SECTION = "devel/python"
LICENSE = "Apache-2.0 | BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=097f805837700cfac572ac274cd38124"

DEPENDS += "python3 python3-cffi python3-asn1crypto python3-pycparser"
RDEPENDS_${PN} = "python3-six"

PR = "r1"

SRCNAME = "cryptography"
SRC_URI = "https://files.pythonhosted.org/packages/69/ed/5e97b7f54237a9e4e6291b6e52173372b7fa45ca730d36ea90b790c0059a/${SRCNAME}-${PV}.tar.gz"

SRC_URI[md5sum] = "27bd1eff5e212a3372a03ef92fd8509a"
SRC_URI[sha256sum] = "4946b67235b9d2ea7d31307be9d5ad5959d6c4a8f98f900157b47abddf698401"

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

LDFLAGS_append = " -pthread"

inherit setuptools3 python3-dir
