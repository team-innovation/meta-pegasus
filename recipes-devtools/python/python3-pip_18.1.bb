DESCRIPTION = "pip for Python"
SECTION = "devel/python"
PRIORITY = "optional"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=593c6cd9d639307226978cbcae61ad4b"
SRCNAME = "pip"
PR = "ml"

DEPENDS += "python3"
SRC_URI = "https://files.pythonhosted.org/packages/45/ae/8a0ad77defb7cc903f09e551d88b443304a9bd6e6f124e75c0fbbf6de8f7/pip-18.1.tar.gz"


S = "${WORKDIR}/${SRCNAME}-${PV}"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native"

NATIVE_INSTALL_WORKS = "1"

inherit setuptools3 python3-dir

SRC_URI[md5sum] = "75cad449ad62c88b22de317a26781714"
SRC_URI[sha256sum] = "c0a292bd977ef590379a3f05d7b7f65135487b67470f6281289a94e015650ea1"
