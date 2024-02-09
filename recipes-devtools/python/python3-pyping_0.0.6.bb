SUMMARY = "Add Python Ping implementation"
SECTION = "devel/python"
PRIORITY = "optional"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://LICENSE;md5=75181f099805ec9c345d64aecd0eae2c"
SRCNAME = "pyping"
PR = "ml1"
SRCREV = "9002e0c310d0ce68ac078bc550cc9245d33610d7"
SRC_URI = "git://github.com/giokara/pyping.git;protocol=https;branch=main"
S = "${WORKDIR}/git"

inherit setuptools3

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native nativesdk"

do_configure_prepend() {
	sed -i "s/ord(/(/g" ${S}/pyping/core.py
}

