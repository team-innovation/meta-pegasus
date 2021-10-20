DESCRIPTION = "googleapis-common-protos"
HOMEPAGE = "https://pypi.org/project/googleapis-common-protos/"
SECTION = "devel/python"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=2deedbf0e737771f60166811e74a6a92"

DEPENDS += "python3 python3-cython"
RDEPENDS_${PN} = "python3-protobuf python3-six"

PR = "r1"

SRC_URI[md5sum] = "302b86e0c3a99b9e2d51070d38980d1e"
SRC_URI[sha256sum] = "c075eddaa2628ab519e01b7d75b76e66c40eaa50fc52758d8225f84708950ef2"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native nativesdk"

inherit setuptools3 pypi
