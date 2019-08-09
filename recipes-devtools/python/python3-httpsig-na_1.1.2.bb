DESCRIPTION = "Python library for signing HTTP signatures. The Na stands for sodium, because this version supports ed25519 signing from the libsodium library"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=10192cdd65101da73ef82fa84f2f7b2f"

PR = "r1"

SRC_URI = "https://github.com/ahknight/httpsig/archive/v${PV}.zip \
    file://0001-Modified-setup.py-to-work-with-python3-for-yocto.patch \
    file://0002-Added-ed25519-signing-via-pysodium-as-optional-depen.patch"

SRC_URI[md5sum] = "b4cccfe3549387403eca9780e1c98207"
SRC_URI[sha256sum] = "5fb8b37d6da5158478ec62f4b636680ab99488644dc85edf0a7b3906d2cf5ec1"

S = "${WORKDIR}/httpsig-${PV}"

RDEPENDS_${PN} = "python3-pysodium"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native"

inherit setuptools3 
