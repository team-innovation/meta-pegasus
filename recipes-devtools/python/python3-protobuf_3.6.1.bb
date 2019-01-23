DESCRIPTION = "Protocol Buffers"
HOMEPAGE = "https://developers.google.com/protocol-buffers/"
SECTION = "devel/python"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://MANIFEST.in;md5=2031594b571227c33958e158d982764c"

PR = "r1"
PROVIDES = "python3-google"
RDEPENDS_${PN} = "python3-six"

SRC_URI = "https://files.pythonhosted.org/packages/1b/90/f531329e628ff34aee79b0b9523196eb7b5b6b398f112bb0c03b24ab1973/protobuf-3.6.1.tar.gz"

SRC_URI[md5sum] = "e2daef80e70249aa1f05363670c6b3f8"
SRC_URI[sha256sum] = "1489b376b0f364bcc6f89519718c057eb191d7ad6f1b395ffd93d1aa45587811"

SRCNAME ="protobuf-${PV}"
S = "${WORKDIR}/${SRCNAME}"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native"

NATIVE_INSTALL_WORKS = "1"

inherit setuptools3 python3-dir
