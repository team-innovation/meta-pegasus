SUMMARY = "Simple ntpd init script"
DESCRIPTION = "This package provides a simple ntpd statup script"
HOMEPAGE = "http://source.vivint.com"
SECTION = "base"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://${WORKDIR}/COPYING;md5=a6d576eb292a14a25860bf932896ef51"
PR = "r1"

SRC_URI = "file://simple-ntpdinit \
           file://COPYING"

do_install () {
	install -d ${D}${sysconfdir}/init.d
	install -m 0755 ${WORKDIR}/simple-ntpdinit ${D}${sysconfdir}/init.d/ntpd

	# Create run level links
	update-rc.d -r ${D} ntpd defaults 85
}
