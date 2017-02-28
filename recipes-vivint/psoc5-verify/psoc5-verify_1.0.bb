SUMMARY = "Slimline PSOC5 Verify Script"
DESCRIPTION = "This package provides s script to verify and reprogram a Slimline PSOC5 device"
HOMEPAGE = "http://git.vivint.com"
SECTION = "base"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://${WORKDIR}/COPYING;md5=a6d576eb292a14a25860bf932896ef51"
PR = "r12"

SRC_URI = "file://psoc5-verify \
           file://COPYING"

do_install () {
	install -d ${D}${sysconfdir}/init.d
	install -m 0755 ${WORKDIR}/psoc5-verify ${D}${sysconfdir}/init.d

	# Create run level links
	update-rc.d -r ${D} psoc5-verify start 07 S .
}
