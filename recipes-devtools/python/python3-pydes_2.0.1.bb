SUMMARY = "Python 3DES Library"
HOMEPAGE = "https://whitemans.ca/des.html"
SECTION = "devel/python"
LICENSE = "PD"
LIC_FILES_CHKSUM = "file://README.txt;md5=970e91632a0a87cfd31760e96da9ac4e"

SRC_URI[md5sum] = "e2b108b1b8e982c609307478960a4257"
SRC_URI[sha256sum] = "e2ab8e21d2b83e90d90dbfdcb6fb8ac0000b813238b7ecaede04f8435c389012"

PYPI_PACKAGE = "pyDes"
inherit setuptools3 pypi

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native nativesdk"
