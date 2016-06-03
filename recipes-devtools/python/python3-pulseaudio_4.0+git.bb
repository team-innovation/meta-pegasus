DESCRIPTION = "libpulseaudio for Python"
SECTION = "devel/python"
PRIORITY = "optional"
LICENSE = "LGPL-2.1"
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
PR = "ml4"
PR_append = "+gitr${SRCREV}"

SRCREV = "7f63becee9955c61b8d8d39c7207fafebed60cb2"
SRC_URI = "git://github.com/thelinuxdude/python-pulseaudio.git;protocol=git;branch=pa-4.0 \
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
