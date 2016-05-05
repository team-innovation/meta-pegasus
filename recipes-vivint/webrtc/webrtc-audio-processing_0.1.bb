DESCRIPTION = "WebRTC AudioProcessing"
SECTION = "libs"
PRIORITY = "optional"
#DEPENDS = "libid3tag"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://COPYING;md5=da08a38a32a340c5d91e13ee86a118f2"
PR = "r2"

SRC_URI = "http://freedesktop.org/software/pulseaudio/webrtc-audio-processing/webrtc-audio-processing-0.1.tar.xz \
	  "

S = "${WORKDIR}/webrtc-audio-processing-${PV}"

inherit autotools pkgconfig

EXTRA_OECONF = "--enable-shared --enable-static"
# The ASO's don't take any account of thumb...
#EXTRA_OECONF_append_thumb = " --disable-aso --enable-fpm=default"
#EXTRA_OECONF_append_arm = " --enable-fpm=arm"

#do_configure_prepend () {
##	damn picky automake...
#	touch NEWS AUTHORS ChangeLog
#}

#ARM_INSTRUCTION_SET = "arm"

SRC_URI[md5sum] = "da25bb27812c8404060d4cc0dc712f04"
SRC_URI[sha256sum] = "ed4b52f9c2688b97628035a5565377d74704d7c04de4254a768df3342c7afedc"
