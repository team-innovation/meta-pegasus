DESCRIPTION = "easydict"
HOMEPAGE = "http://docs.python-requests.org/en/latest"
SECTION = "devel/python"
LICENSE = "GPL-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=e6a600fd5e1d9cbde2d983680233ad02"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native"

inherit setuptools3 pypi

SRC_URI[md5sum] = "5d64bc4baa7178da19b6c6a688f55522"
SRC_URI[sha256sum] = "f1ec91110737a62fe28d14970ffa7a7c7b441a32e35a6f3da6a6082ffb7f9432"

