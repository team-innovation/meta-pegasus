SUMMARY = "Helper scripts for FCC testing"
DESCRIPTION = "Adds scripts used for turning on/off hw components"
SECTION = "base"
LICENSE = "CLOSED"

RDEPENDS_${PN} += "alsa-utils-amixer"

SRC_URI = "file://fcc-helper.sh \
"

do_install () {
	install -d ${D}/usr/bin/
	install -m 0755 ${WORKDIR}/fcc-helper.sh ${D}/usr/bin/fcc-helper
}

PACKAGE_ARCH = "${MACHINE_ARCH}"
