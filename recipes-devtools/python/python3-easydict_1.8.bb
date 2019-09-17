DESCRIPTION = "easydict"
HOMEPAGE = "http://docs.python-requests.org/en/latest"
SECTION = "devel/python"
LICENSE = "GPL-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=e6a600fd5e1d9cbde2d983680233ad02"

PR = "r1"
SRCNAME = "easydict-${PV}"

SRC_URI = "https://files.pythonhosted.org/packages/e7/24/1fbad1ef849573ea97983e29761160462d89b95dc71853f1080c281ac964/easydict-1.8.tar.gz"

SRC_URI[md5sum] = "5d64bc4baa7178da19b6c6a688f55522"
SRC_URI[sha256sum] = "f1ec91110737a62fe28d14970ffa7a7c7b441a32e35a6f3da6a6082ffb7f9432"

S = "${WORKDIR}/${SRCNAME}"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native"

NATIVE_INSTALL_WORKS = "1"

inherit setuptools3 python3-dir
