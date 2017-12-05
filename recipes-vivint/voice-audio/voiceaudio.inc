# voiceaudio OE build file
# Copyright (C) 2015, Vivint
# 

DESCRIPTION = "User Voice Audio for Sly"
SECTION = "utils"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://${WORKDIR}/COPYING;md5=be94729c3d0e226497bf9ba8c384e96f"
PR = "r79"
PV = "0.1.1+git${SRCPV}"

GIT_AUDIO_SERVER ?= "${GIT_SERVER}"
GIT_AUDIO_BRANCH ?= "develop"
GIT_AUDIO_PROTOCOL ?= "ssh"
GIT_AUDIO_REV ?= "${AUTOREV}"

SRCREV = "${GIT_AUDIO_REV}"
DEPENDS += "alsa-lib pulseaudio"

MODULE = "voice-audio"
SRC_URI = "git://${GIT_AUDIO_SERVER}/audio;protocol=${GIT_AUDIO_PROTOCOL};branch=${GIT_AUDIO_BRANCH}"
RDEPENDS_${PN} = "libpulse-simple libpulse libasound"
S = "${WORKDIR}/git/vaudio-sly"
do_compile() {
   oe_runmake
}

#TARGET_CC_ARCH += "${LDFLAGS}"

do_install_append() {
	install -d ${D}/usr/local/bin
	install -m 0755 ${S}/vaudio ${D}/usr/local/bin
}

FILES_${PN}-dbg += "/usr/local/bin/.debug"
FILES_${PN} = "/usr/local/bin/*"
