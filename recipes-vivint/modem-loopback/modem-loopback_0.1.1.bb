DESCRIPTION = "User modem loopback test for Wallsly"
SECTION = "utils"
LICENSE = "BSD-2-Clause"
LIC_FILES_CHKSUM = "file://COPYING;md5=b022f53d2c5f4c04151c3eb748ef18a8"
PR = "r3"

SRCREV = "${AUTOREV}"
SRC_URI = "git://git.vivint.com/modem-loopback;protocol=git;branch=master"

RDEPENDS_${PN} = "libpulse-simple libpulse libasound"

S = "${WORKDIR}/git"

do_compile() {
   oe_runmake
}

do_install() {
    install -d ${D}${bindir}
    cp -a ${S}/modem-loopback ${D}${bindir}
}

FILES_${PN} = "${bindir}/modem-loopback"

