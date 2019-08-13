FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

PR = "r2"

SRC_URI += "file://mosquitto.conf \
	    file://mosquitto \
"

do_install_append() {
    install -d ${D}${sysconfdir}/mosquitto
    install -m 0644 ${WORKDIR}/mosquitto.conf ${D}/${sysconfdir}/mosquitto/

    install -d ${D}${sysconfdir}/procman.d
    install -m 0755 ${WORKDIR}/mosquitto ${D}/${sysconfdir}/procman.d/
}


FILES_${PN} += "${sysconfdir}/mosquitto \
               ${sysconfdir}/procman.d/mosquitto\
"
