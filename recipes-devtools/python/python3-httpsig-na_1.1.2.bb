DESCRIPTION = "Python library for signing HTTP signatures. The Na stands for sodium, because this version supports ed25519 signing from the libsodium library"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=10192cdd65101da73ef82fa84f2f7b2f"

PR = "r1"

SRC_URI = "https://github.com/ahknight/httpsig/archive/v1.1.2.zip \
    file://0001-Modified-setup.py-to-work-with-python3-for-yocto.patch \
    file://0002-Added-ed25519-signing-via-pysodium-as-optional-depen.patch"
SRC_URI[md5sum] = "fe46cf96912892e5d0aaba47a3abc9de"
SRC_URI[sha256sum] = "e6f9a0cae20c0452fea40d45e68d97aa2843a89b5903f7fd3b86a45b4f74f2f8"

S = "${WORKDIR}/httpsig-${PV}"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native"

NATIVE_INSTALL_WORKS = "1"

inherit distutils3
