SUMMARY = "Networking init scripts and configuration files to support wps"
DESCRIPTION = "This package provides high level tools to connect wlan0 network interfaces"
HOMEPAGE = "http://source.vivint.com"
SECTION = "base"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://${WORKDIR}/COPYING;md5=be94729c3d0e226497bf9ba8c384e96f"
PR = "r8"

SRC_URI = "file://network_func \
           file://udhcpc_wlan0 \
           file://COPYING \
           file://wps.sh \
           file://network_conf_verify \
           file://stop_wps.sh"

do_install () {
	install -d ${D}${sysconfdir}/init.d ${D}${sysconfdir}/network ${D}/usr/local/bin
	install -m 0644 ${WORKDIR}/network_func ${D}${sysconfdir}/network
	install -m 0755 ${WORKDIR}/udhcpc_wlan0 ${D}${sysconfdir}/init.d
	install -m 0755 ${WORKDIR}/wps.sh ${D}/usr/local/bin
	install -m 0755 ${WORKDIR}/stop_wps.sh ${D}/usr/local/bin
        install -m 0755 ${WORKDIR}/network_conf_verify ${D}${sysconfdir}/init.d

	# Creat run level links
	update-rc.d -r ${D} network_conf_verify start 41 S 3 .
}

FILES_${PN} += "/usr/local/bin/*"
