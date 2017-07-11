DESCRIPTION = "User asr parser for Wallsly Microsemi"
SECTION = "utils"
LICENSE = "BSD-2-Clause"
LIC_FILES_CHKSUM = "file://COPYING;md5=b022f53d2c5f4c04151c3eb748ef18a8"
PR = "r5"

SRCREV = "${AUTOREV}"
SRC_URI = "git://${GIT_SERVER}/audio.git;protocol=ssh;branch=develop"
RDEPENDS_${PN} = "libpulse-simple libpulse libasound"

S = "${WORKDIR}/git/asr-parse"

do_compile() {
   oe_runmake
}

do_install_append() {
        install -d ${D}/usr/local/bin
        install -m 0755 ${S}/asr-parse ${D}/usr/local/bin
}

FILES_${PN}-dbg += "/usr/local/bin/.debug"
FILES_${PN} = "/usr/local/bin/*"
