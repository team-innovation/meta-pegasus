SUMMARY = "Audio init scripts and configuration files to support ALSA on a Slimline device"
DESCRIPTION = "This package provides startup scripts to configure ALSA on a Slimline device"
HOMEPAGE = "http://source.vivint.com"
SECTION = "base"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://${WORKDIR}/COPYING;md5=be94729c3d0e226497bf9ba8c384e96f"
PR = "r13"

SRC_URI = "file://COPYING \
           file://audio.sh"

inherit update-rc.d

INITSCRIPT_NAME = "audio.sh"
INITSCRIPT_PARAMS = "start 38 S ."

do_install () {
	install -d ${D}${sysconfdir}/init.d
	install -m 0755 ${WORKDIR}/audio.sh ${D}${sysconfdir}/init.d
}

FILES_${PN} += "/usr/local/bin/*"
