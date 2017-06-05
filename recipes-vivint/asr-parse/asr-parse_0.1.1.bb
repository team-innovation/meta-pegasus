DESCRIPTION = "User asr parser for Wallsly Microsemi"
SECTION = "utils"
LICENSE = "BSD-2-Clause"
LIC_FILES_CHKSUM = "file://COPYING;md5=d73bd2cc27d46d711cf1ba1d489eaa43"
PR = "r0"

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
