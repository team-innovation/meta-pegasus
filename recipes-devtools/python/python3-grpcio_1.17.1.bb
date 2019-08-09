DESCRIPTION = "grpcio"
HOMEPAGE = "https://pypi.python.org/pypi/grpcio"
SECTION = "devel/python"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://README.md;md5=d2e7d1e6fa6808c1d647b186c9ed1874"

DEPENDS += "python3 python3-cython"
RDEPENDS_${PN} = "python3-protobuf python3-six"

PR = "r3"

SRC_URI += "file://fix-deadlock.patch"
SRC_URI += "file://disable-grpc-forking.patch"

SRC_URI[md5sum] = "dd46d7270dfce713c03f45fae999cae3"
SRC_URI[sha256sum] = "fd6774bbb6c717f725b39394757445ead4f69c471118364933aadb81a4f16961"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native"

inherit setuptools3 pypi
