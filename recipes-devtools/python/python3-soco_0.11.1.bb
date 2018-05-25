DESCRIPTION = "Python SoCo"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE.rst;md5=07b0e2ca9ac77cd65cd4edf2e13367ea"
PR = "r2"
SRCNAME = "soco"

DEPENDS += "python3"
RDEPENDS_${PN} = "\
  python3-xmltodict \
  python3-requests \
"

SRC_URI = "file://soco-0.11.1.tar.gz \
	   "

SRC_URI[md5sum] = "fdbcc8ef14b6e9199a894de160eb5730"

S = "${WORKDIR}/${SRCNAME}-${PV}"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native"

NATIVE_INSTALL_WORKS = "1"

inherit setuptools3 python3-dir
