SUMMARY = "libpulseaudio for Python"
HOMEPAGE = "https://source.vivint.com/projects/EM/repos/python-pulseaudio/browse"
SECTION = "devel/python"
LICENSE = "LGPL-2.1"
LIC_FILES_CHKSUM = "file://LICENSE;md5=9df70e0e8d4e7a719b31d430c2e41447"

inherit setuptools3

SRCREV = "d2b2809e2ef39e5e7e469b0c224714d6f8778ade"
SRCNAME = "python-pulseaudio"
S = "${WORKDIR}/git"

SRC_URI = " \
    git://git@source.vivint.com:7999/em/python-pulseaudio.git;protocol=ssh;branch=pa-12.2 \
"

RDEPENDS_${PN} = "\
  ${PYTHON_PN}-ctypes \
  libpulse \
  libpulse-simple \
"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native nativesdk"
