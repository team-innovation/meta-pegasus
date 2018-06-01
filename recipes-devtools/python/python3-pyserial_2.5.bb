DESCRIPTION = "Serial Port Support for Python"
SECTION = "devel/python"
LICENSE = "PSF"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=5003807a6b006d7d522a3aaae05443b7"
SRCNAME = "pyserial"
PR = "ml5"

SRC_URI = "https://pypi.python.org/packages/source/p/pyserial/pyserial-${PV}.tar.gz \
	file://miniterm.patch"
S = "${WORKDIR}/${SRCNAME}-${PV}"

inherit setuptools3 python3-dir

# FIXME might stop packaging serialwin32 and serialjava files

DEPENDS += "python3"

RDEPENDS_${PN} = "python3-core"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native"

NATIVE_INSTALL_WORKS = "1"

do_install_prepend() {
    install -d ${D}/${libdir}/${PYTHON_DIR}/site-packages
}

RDEPENDS_${PN} += "\
  python3-fcntl \
  python3-io \
  python3-stringold \
"

RDEPENDS_${PN}_class-native = ""

SRC_URI[md5sum] = "34340820710239bea2ceca7f43ef8cab"
SRC_URI[sha256sum] = "eddd22280e0dac0888c6cddd8906ebd902fa42467fee151c43ecde4196bbf511"
