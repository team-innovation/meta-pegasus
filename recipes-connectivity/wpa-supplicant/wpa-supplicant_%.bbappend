FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

PR = "r2"

SRC_URI += "file://wpa_supplicant_p2p0.conf"

do_install_append() {
	install -d ${D}${sysconfdir}
	install -m 0644 ${WORKDIR}/wpa_supplicant_p2p0.conf ${D}/${sysconfdir}
}

FILES_${PN} += "${sysconfdir}/wpa_supplicant_p2p0.conf"
