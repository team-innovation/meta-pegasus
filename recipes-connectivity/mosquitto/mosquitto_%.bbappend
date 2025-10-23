FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://mosquitto.conf \
            file://mosquitto.logrotate"

do_install:append() {
    install -d ${D}${sysconfdir}/mosquitto
    install -d ${D}${sysconfdir}/logrotate.d
    install -m 0644 ${WORKDIR}/mosquitto.conf ${D}/${sysconfdir}/mosquitto/
    install -m 0644 ${WORKDIR}/mosquitto.logrotate ${D}/${sysconfdir}/logrotate.d/mosquitto
    touch ${D}${sysconfdir}/mosquitto/password_file
}


FILES:${PN} += "${sysconfdir}/mosquitto \
               ${sysconfdir}/logrotate.d/mosquitto \
               ${sysconfdir}/procman.d/mosquitto \
	       ${sysconfdir}/init.d/mosquitto.init \
"
