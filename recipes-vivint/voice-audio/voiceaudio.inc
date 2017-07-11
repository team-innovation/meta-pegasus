# voiceaudio OE build file
# Copyright (C) 2015, Vivint
# 

DESCRIPTION = "Voice Audio Daemon"
SECTION = "devel"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://${WORKDIR}/COPYING;md5=be94729c3d0e226497bf9ba8c384e96f"
INC_PR = "r75"

SRCREV = "f093bc1fe4c7aeab064a214a90e68216d9d54195"
MODULE = "voice-audio"
SRC_URI = "git://git@${GIT_SERVER}/${MODULE}.git;protocol=ssh \
   	file://COPYING \
	file://libvocal.a"

#SRC_URI = "file:///media/WD_MPassport/live555 \
#	       file://config.linux-cross-shared-libraries"


S = "${WORKDIR}/git"
TARGET_CC_ARCH += "${LDFLAGS}"

DEPENDS = "pulseaudio"
RDEPENDS_${PN} = "libpulse-simple libpulse libasound"
#	 libvocal"

do_configure() {
	cp ${WORKDIR}/libvocal.a ${STAGING_LIBDIR}
}

do_install_append() {
	install -d ${D}/usr/local/bin
	install -m 0755 ${S}/vaudio ${D}/usr/local/bin
}

FILES_${PN}-dbg += "/usr/local/bin/.debug"
FILES_${PN} = "/usr/local/bin/*"
