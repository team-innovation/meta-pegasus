DESCRIPTION = "A Pure Python Expect-u like Module for Python"
SECTION = "devel/python"
PRIORITY = "optional"
LICENSE = "ISC"
LIC_FILES_CHKSUM = "file://LICENSE;md5=1c7a725251880af8c6a148181665385b"
SRCNAME = "pexpect"
PR = "ml1"

SRC_URI = "https://pypi.python.org/packages/source/p/pexpect/${SRCNAME}-${PV}.tar.gz"

S = "${WORKDIR}/${SRCNAME}-${PV}"

inherit pythonnative

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native"

NATIVE_INSTALL_WORKS = "1"

inherit setuptools3 python3-dir

RDEPENDS_${PN} = "\
  python3-core \
  python3-io \
  python3-terminal \
  python3-resource \
  python3-fcntl \
  python3-ptyprocess \
"

RDEPENDS_${PN}_class-native = ""

SRC_URI[md5sum] = "8071ec5df0f3d515daedafad672d1632"
SRC_URI[sha256sum] = "bf6816b8cc8d301a499e7adf338828b39bc7548eb64dbed4dd410ed93d95f853"
