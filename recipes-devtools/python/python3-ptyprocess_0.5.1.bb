DESCRIPTION = "A Pure Python subshell launcher for Python"
SECTION = "devel/python"
PRIORITY = "optional"
LICENSE = "ISC"
LIC_FILES_CHKSUM = "file://LICENSE;md5=cfdcd51fa7d5808da4e74346ee394490"
SRCNAME = "ptyprocess"
PR = "ml1"

SRC_URI = "https://pypi.python.org/packages/source/p/ptyprocess/${SRCNAME}-${PV}.tar.gz"

S = "${WORKDIR}/${SRCNAME}-${PV}"

inherit pythonnative

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native"

NATIVE_INSTALL_WORKS = "1"

inherit setuptools3

RDEPENDS_${PN} = "\
  python3-core \
  python3-io \
  python3-terminal \
  python3-resource \
  python3-fcntl \
"

RDEPENDS_${PN}_class-native = ""

SRC_URI[md5sum] = "94e537122914cc9ec9c1eadcd36e73a1"
SRC_URI[sha256sum] = "0530ce63a9295bfae7bd06edc02b6aa935619f486f0f1dc0972f516265ee81a6"
