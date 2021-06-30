FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += " \
	file://compile_fixes.patch \
	file://iotivity-bridging.pc \
	file://10-dummy0.netdev \
        file://10-dummy0.network \
"

do_install_append () {
	# add dummy0 interface to systemd network setup
	install -d ${D}${systemd_unitdir}/network
	install -m 0644 ${WORKDIR}/10-dummy0.netdev ${D}${systemd_unitdir}/network
	install -m 0644 ${WORKDIR}/10-dummy0.network ${D}${systemd_unitdir}/network

	install -d ${D}${libdir}/pkgconfig
	install -m 0644 ${WORKDIR}/iotivity-bridging.pc ${D}${libdir}/pkgconfig
}

FILES_${PN} += "${systemd_unitdir}/network/10-dummy0.netdev"
FILES_${PN} += "${systemd_unitdir}/network/10-dummy0.network"

FILES_${PN}-dev += "${libdir}/pkgconfig"

# Iotivity needs optimization for FORTIFY_SOURCES flag
CC_append = " -Os"
CXX_append = " -Os"
CPP_append = " -Os"

PARALLEL_MAKE = "-j 1"
