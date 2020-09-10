FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://mosquitto.conf" 

do_install_append() {
    install -d ${D}${sysconfdir}/mosquitto
    install -m 0644 ${WORKDIR}/mosquitto.conf ${D}/${sysconfdir}/mosquitto/
}


FILES_${PN} += "${sysconfdir}/mosquitto \
               ${sysconfdir}/procman.d/mosquitto \
	       ${sysconfdir}/init.d/mosquitto.init \
"
