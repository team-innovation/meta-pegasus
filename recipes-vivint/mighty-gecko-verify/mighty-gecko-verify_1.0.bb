SUMMARY = "Slimline Mighty Gecko Verify Script"
DESCRIPTION = "This package provides s script to verify and reprogram a Slimline Mighty Gecko device"
HOMEPAGE = "http://source.vivint.com"
SECTION = "base"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://${WORKDIR}/COPYING;md5=a6d576eb292a14a25860bf932896ef51"
PR = "r12"

SRC_URI = "file://mighty-gecko-verify \
           file://COPYING"

do_install () {
	install -d ${D}${sysconfdir}/init.d
	install -m 0755 ${WORKDIR}/mighty-gecko-verify ${D}${sysconfdir}/init.d

	# Create run level links
	update-rc.d -r ${D} mighty-gecko-verify start 08 S .
}
