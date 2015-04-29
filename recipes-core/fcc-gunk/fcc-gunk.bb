SUMMARY = "Helper scripts for FCC testing"
DESCRIPTION = "Adds scripts used for turning on/off hw components"
SECTION = "base"
LICENSE = "CLOSED"

RDEPENDS_${PN} += "alsa-utils-amixer"

SRC_URI = "file://audio-helper.sh \
"

do_install () {
	install -d ${D}/usr/bin/
	install -m 0755 ${WORKDIR}/audio-helper.sh ${D}/usr/bin/audio-helper
}

PACKAGE_ARCH = "${MACHINE_ARCH}"
