DESCRIPTION = "MessagePack (de)serializer"
HOMEPAGE = "https://pypi.python.org/pypi/msgpack-python/"
SECTION = "devel/python"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=2574f7f6219c48304b68fe5a1063fdd2"

PR = "r2"
SRCNAME = "intelhex"

SRC_URI = "https://pypi.python.org/packages/source/I/IntelHex/${SRCNAME}-${PV}.tar.gz"


SRC_URI[md5sum] = "484c91b23d703bbdb043d7e19dfff799"
SRC_URI[sha256sum] = "11eeb14534a84218b59094c2b76fbde448cac08613be0925efbcf1432ea0bbec"


S = "${WORKDIR}/${SRCNAME}-${PV}"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native"

NATIVE_INSTALL_WORKS = "1"

inherit setuptools3 python3-dir
