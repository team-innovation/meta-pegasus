DESCRIPTION = "libpulseaudio for Python"
SECTION = "devel/python"
PRIORITY = "optional"
LICENSE = "LGPL-2.1"
LIC_FILES_CHKSUM = "\
   file://LICENSE;md5=9df70e0e8d4e7a719b31d430c2e41447 \
"

inherit setuptools3 

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native nativesdk"

SRCNAME = "python-pulseaudio"
PR = "ml7"
PR_append = "+gitr${SRCREV}"

SRCREV = "6ed0449c51967c49b16096bfe41631458807916d"
SRC_URI = "git://github.com/thelinuxdude/python-pulseaudio.git;protocol=https;branch=pa-10.0 \
"
S = "${WORKDIR}/git"

RDEPENDS_${PN} = "\
  python3-ctypes \
  libpulse \
  libpulse-simple \
"
