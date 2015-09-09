DESCRIPTION = "libpulseaudio for Python"
SECTION = "devel/python"
PRIORITY = "optional"
LICENSE = "LGPL"
LIC_FILES_CHKSUM = "\
   file://LICENSE;md5=9df70e0e8d4e7a719b31d430c2e41447 \
"

DEPENDS += "python3"
DEPENDS_virtclass-native += "python3-native"

inherit distutils3

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native"

NATIVE_INSTALL_WORKS = "1"

SRCNAME = "python-pulseaudio"
PR = "ml5"
PR_append = "+gitr${SRCREV}"

SRCREV = "4eebfc9f693ec6a56b19f629543a1eb30e973c2d"
SRC_URI = "git://github.com/thelinuxdude/python-pulseaudio.git;protocol=git;branch=pa-5.0 \
"
S = "${WORKDIR}/git"

do_install_prepend() {
    install -d ${D}/${libdir}/${PYTHON_DIR}/site-packages
}

RDEPENDS_${PN} = "\
  python3-distutils \
  python3-ctypes \
  libpulse \
  libpulse-simple \
"
