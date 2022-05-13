DESCRIPTION = "libpulseaudio for Python"
SECTION = "devel/python"
PRIORITY = "optional"
LICENSE = "LGPL-2.1"
LIC_FILES_CHKSUM = "\
   file://LICENSE;md5=9df70e0e8d4e7a719b31d430c2e41447 \
"

DEPENDS += "python3"

inherit setuptools3

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native nativesdk"

SRCNAME = "python-pulseaudio"
PR = "ml7"
SRCREV = "${AUTOREV}"

SRC_URI = "git://${GIT_SERVER}/python-pulseaudio.git;protocol=ssh;branch=pa-12.2 \
"
S = "${WORKDIR}/git"

RDEPENDS_${PN} = "\
  python3-ctypes \
  libpulse \
  libpulse-simple \
"
