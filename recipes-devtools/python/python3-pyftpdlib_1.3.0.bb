DESCRIPTION = "High-level asynchronous FTP server library"
HOMEPAGE = "https://code.google.com/p/pyftpdlib/"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=05f70340c001186bec994c0b15265d8d"

PR = "r1"
SRCNAME = "pyftpdlib"

SRC_URI = "https://pypi.python.org/packages/source/p/${SRCNAME}/${SRCNAME}-${PV}.tar.gz"

SRC_URI[md5sum] = "758cd44d3b2c6bf91bfc6f52b959afe1"
SRC_URI[sha256sum] = "df022c94d1883f596b8004bc33a9ba7e6ad9a07a794c70061dd08370ca320fc6"

S = "${WORKDIR}/${SRCNAME}-${PV}"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native"

NATIVE_INSTALL_WORKS = "1"

inherit distutils3
