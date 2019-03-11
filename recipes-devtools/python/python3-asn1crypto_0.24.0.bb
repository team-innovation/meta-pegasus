DESCRIPTION = "asn1crypto"
HOMEPAGE = "https://pypi.org/project/asn1crypto"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=52010cd3c7d7bd965b55721ef4d93ec2"

DEPENDS += "python3"
#RDEPENDS_${PN} = ""

PR = "r0"

SRCNAME = "asn1crypto"
SRC_URI = "https://files.pythonhosted.org/packages/fc/f1/8db7daa71f414ddabfa056c4ef792e1461ff655c2ae2928a2b675bfed6b4/${SRCNAME}-${PV}.tar.gz"

SRC_URI[md5sum] = "de3520426e81a6581352d4366f310eb1"
SRC_URI[sha256sum] = "9d5c20441baf0cb60a4ac34cc447c6c189024b6b4c6cd7877034f4965c464e49"

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
