DESCRIPTION = "User Voice Audio for Sly"
SECTION = "utils"
LICENSE = "BSD-2-Clause"
LIC_FILES_CHKSUM = "file://COPYING;md5=b022f53d2c5f4c04151c3eb748ef18a8"
PR = "r79"
PV = "0.1.1+git${SRCPV}"

GIT_AUDIO_SERVER ?= "${GIT_SERVER}"
GIT_AUDIO_BRANCH ?= "develop"
GIT_AUDIO_PROTOCOL ?= "ssh"
GIT_AUDIO_REV ?= "${AUTOREV}"

SRCREV = "${GIT_AUDIO_REV}"
DEPENDS += "alsa-lib pulseaudio pkgconfig-native"

MODULE = "voice-audio"
SRC_URI = "git://${GIT_AUDIO_SERVER}/audio;protocol=${GIT_AUDIO_PROTOCOL};branch=${GIT_AUDIO_BRANCH} \
	   file://libvocal.a\
	   "

RDEPENDS_${PN} = "libpulse-simple libpulse libasound"
S = "${WORKDIR}/git/vaudio-sly"
do_compile() {
   oe_runmake
}

#TARGET_CC_ARCH += "${LDFLAGS}"

do_configure() {
        cp ${WORKDIR}/libvocal.a ${STAGING_LIBDIR}
}

do_install_append() {
	install -d ${D}/usr/local/bin
	install -m 0755 ${S}/vaudio ${D}/usr/local/bin
}

FILES_${PN}-dbg += "/usr/local/bin/.debug"
FILES_${PN} = "/usr/local/bin/*"
