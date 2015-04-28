SUMMARY = "Networking init scripts and configuration files to support wps"
DESCRIPTION = "This package provides high level tools to connect wlan0 network interfaces"
HOMEPAGE = "http://git.vivint.com"
SECTION = "base"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://${WORKDIR}/COPYING;md5=be94729c3d0e226497bf9ba8c384e96f"
PR = "r1"

SRC_URI = "file://network_func \
           file://udhcpc_wlan0 \
           file://COPYING \
           file://wps.sh"

do_install () {
	install -d ${D}${sysconfdir}/init.d ${D}${sysconfdir}/network ${D}/usr/local/bin
	install -m 0644 ${WORKDIR}/network_func ${D}${sysconfdir}/network
	install -m 0755 ${WORKDIR}/udhcpc_wlan0 ${D}${sysconfdir}/init.d
	install -m 0755 ${WORKDIR}/wps.sh ${D}/usr/local/bin

	# Creat run level links
	update-rc.d -r ${D} udhcpc_wlan0 start 41 3 4 5 . stop 41 6 .
}

FILES_${PN} += "/usr/local/bin/*"
