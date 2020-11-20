FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
PR = "6"

SRC_URI += "file://hostapd.conf \
	   file://hostapd_hub.conf"

INITSCRIPT_PARAMS = "stop 30 2 3 4 5 . " 
INITSCRIPT_NAME = "hostapd"

do_install_append () {
    install -m 0644 ${WORKDIR}/hostapd.conf ${D}${sysconfdir}
    install -m 0644 ${WORKDIR}/hostapd_hub.conf ${D}${sysconfdir}
}

