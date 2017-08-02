CRIPTION = "User mfr-audio-test"
SECTION = "utils"
LICENSE = "BSD-2-Clause"
LIC_FILES_CHKSUM = "file://COPYING;md5=b022f53d2c5f4c04151c3eb748ef18a8"
PR = "r1"
PV = "0.1.1+git${SRCPV}"
SRCREV = "${AUTOREV}"

GIT_AUDIO_SERVER ?= "${GIT_SERVER}"
GIT_AUDIO_BRANCH ?= "develop"
GIT_AUDIO_PROTOCOL ?= "ssh"

SRC_URI = "git://${GIT_AUDIO_SERVER}/audio;protocol=${GIT_AUDIO_PROTOCOL};branch=${GIT_AUDIO_BRANCH}"
RDEPENDS_${PN} = "libpulse-simple libpulse libasound"
S = "${WORKDIR}/git/mfr-piezo-test"
do_compile() {
   oe_runmake
}

do_install_append() {
        install -d ${D}/usr/local/bin
        install -m 0755 ${S}/mfr-piezo-test ${D}/usr/local/bin
}

FILES_${PN}-dbg += "/usr/local/bin/.debug"
FILES_${PN} = "/usr/local/bin/*"
