CRIPTION = "User vaudio-wallsly for Wallsly Microsemi"
SECTION = "utils"
LICENSE = "BSD-2-Clause"
LIC_FILES_CHKSUM = "file://COPYING;md5=b022f53d2c5f4c04151c3eb748ef18a8"
PR = "r5"
PV = "0.1.1+git${SRCPV}"

SRCREV = "${AUTOREV}"
GIT_AUDIO_SERVER ?= "${GIT_SERVER}"
GIT_AUDIO_BRANCH ?= "develop"
SRC_URI = "git://${GIT_AUDIO_SERVER}/audio;protocol=ssh;branch=${GIT_AUDIO_BRANCH}"
RDEPENDS_${PN} = "libpulse-simple libpulse libasound"
S = "${WORKDIR}/git/vaudio-wallsly"
do_compile() {
   oe_runmake
}

do_install_append() {
        install -d ${D}/usr/local/bin
        install -m 0755 ${S}/vaudio-wallsly ${D}/usr/local/bin
}

FILES_${PN}-dbg += "/usr/local/bin/.debug"
FILES_${PN} = "/usr/local/bin/*"
