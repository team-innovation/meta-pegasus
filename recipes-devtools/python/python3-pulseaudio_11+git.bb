DESCRIPTION = "libpulseaudio for Python"
SECTION = "devel/python"
PRIORITY = "optional"
LICENSE = "LGPL-2.1"
LIC_FILES_CHKSUM = "\
   file://${WORKDIR}/git/LICENSE;md5=9df70e0e8d4e7a719b31d430c2e41447 \
"

DEPENDS += "python3"

inherit setuptools3 python3-dir

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native"

NATIVE_INSTALL_WORKS = "1"

SRCNAME = "python-pulseaudio"
PR = "ml7"
SRCREV = "${AUTOREV}"

SRC_URI = "git://git@source.vivint.com:7999/em/python-pulseaudio.git;protocol=ssh;branch=pa-11.1 \
"
S = "${WORKDIR}/git"

do_install_prepend() {
    install -d ${D}/${libdir}/${PYTHON_DIR}/site-packages
}

RDEPENDS_${PN} = "\
  python3-ctypes \
  libpulse \
  libpulse-simple \
"
