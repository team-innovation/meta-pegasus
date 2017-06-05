DESCRIPTION = "User asr parser for Wallsly Microsemi"
SECTION = "utils"
LICENSE = "BSD-2-Clause"
LIC_FILES_CHKSUM = "file://COPYING;md5=b022f53d2c5f4c04151c3eb748ef18a8"
PR = "r1"

SRCREV = "${AUTOREV}"
SRC_URI = "git://git.vivint.com/asr-parse;protocol=git;branch=master"
RDEPENDS_${PN} = "libpulse-simple libpulse libasound"

S = "${WORKDIR}/git"

do_compile() {
   oe_runmake
}

do_install() {
    install -d ${D}${bindir}
    cp -a ${S}/asr-parse ${D}${bindir}
}

FILES_${PN} = "${bindir}/asr-parse"
