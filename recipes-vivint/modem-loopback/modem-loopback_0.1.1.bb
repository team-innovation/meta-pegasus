DESCRIPTION = "User modem loopback test for Wallsly"
SECTION = "utils"
LICENSE = "BSD-2-Clause"
LIC_FILES_CHKSUM = "file://COPYING;md5=d73bd2cc27d46d711cf1ba1d489eaa43"
PR = "r2"

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

