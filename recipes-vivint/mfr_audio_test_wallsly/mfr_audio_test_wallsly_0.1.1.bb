DESCRIPTION = "User mfr_audio_test for Wallsly Microsemi"
SECTION = "utils"
LICENSE = "BSD-2-Clause"
LIC_FILES_CHKSUM = "file://COPYING;md5=d73bd2cc27d46d711cf1ba1d489eaa43"
PR = "r0"
SRCREV = "${AUTOREV}"
SRC_URI = "git://git.vivint.com/mfr_audio_test_wallsly;protocol=git;branch=master"
RDEPENDS_${PN} = "libpulse-simple libpulse libasound"
S = "${WORKDIR}/git"
do_compile() {
   oe_runmake
}
#do_install() {
#    install -d ${D}${bindir}
#    cp -a ${S}/mfr_audio_test_wallsly ${D}${bindir}
#}
#FILES_${PN} = "${bindir}/mfr_audio_test_wallsly"

do_install_append() {
        install -d ${D}/usr/local/bin
        install -m 0755 ${WORKDIR}/${MODULE}/git/mfr_audio_test_wallsly ${D}/usr/local/bin
}

FILES_${PN}-dbg += "/usr/local/bin/.debug"
FILES_${PN} = "/usr/local/bin/*"
