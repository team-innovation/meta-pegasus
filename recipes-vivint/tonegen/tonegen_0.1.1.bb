DESCRIPTION = "User tonegen for Wallsly, Glance, Hub, Touchlink, and Sly"
SECTION = "utils"
LICENSE = "BSD-2-Clause"
LIC_FILES_CHKSUM = "file://COPYING;md5=b022f53d2c5f4c04151c3eb748ef18a8"
PR = "r0"
PV = "0.1.1+git${SRCPV}"

DEPENDS += "alsa-lib pulseaudio"

GIT_AUDIO_SERVER ?= "${GIT_SERVER}"
GIT_AUDIO_BRANCH ?= "develop"
GIT_AUDIO_PROTOCOL ?= "ssh"
GIT_AUDIO_REV ?= "${AUTOREV}"

SRCREV = "${GIT_AUDIO_REV}"
SRC_URI = "git://${GIT_AUDIO_SERVER}/audio;protocol=${GIT_AUDIO_PROTOCOL};branch=${GIT_AUDIO_BRANCH}"
RDEPENDS_${PN} = "libpulse-simple libpulse libasound"
S = "${WORKDIR}/git/tonegen"
do_compile() {
   oe_runmake
}

do_install_append() {
        install -d ${D}/usr/local/bin
        install -m 0755 ${S}/tonegen ${D}/usr/local/bin
}

FILES_${PN}-dbg += "/usr/local/bin/.debug"
FILES_${PN} = "/usr/local/bin/*"
