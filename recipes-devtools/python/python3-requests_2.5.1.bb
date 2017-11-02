DESCRIPTION = "Requests - HTTP for Humans"
HOMEPAGE = "http://docs.python-requests.org/en/latest"
SECTION = "devel/python"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=c7869e52c8275537186de35e3cd5f9ec"

PR = "r1"
SRCNAME = "requests-${PV}"

SRC_URI = "https://pypi.python.org/packages/source/r/requests/requests-${PV}.tar.gz"

SRC_URI[md5sum] = "c270eb5551a02e8ab7a4cbb83e22af2e"
SRC_URI[sha256sum] = "7b7735efd3b1e2323dc9fcef060b380d05f5f18bd0f247f5e9e74a628279de66"

S = "${WORKDIR}/${SRCNAME}"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native"

NATIVE_INSTALL_WORKS = "1"

inherit setuptools3
