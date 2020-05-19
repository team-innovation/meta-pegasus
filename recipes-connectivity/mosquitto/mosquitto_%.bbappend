FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

PR = "r2"

SRC_URI += "file://mosquitto.conf \
	    file://mosquitto \
	    file://mosquitto.init \
"
inherit update-rc.d

INITSCRIPT_NAME = "mosquitto.init"
INITSCRIPT_PARAMS = "defaults 87"

do_install_append() {
    install -d ${D}${sysconfdir}/mosquitto
    install -m 0644 ${WORKDIR}/mosquitto.conf ${D}/${sysconfdir}/mosquitto/

    install -d ${D}${sysconfdir}/init.d
    install -m 0755 ${WORKDIR}/mosquitto.init ${D}/${sysconfdir}/init.d/


    install -d ${D}${sysconfdir}/procman.d
    install -m 0755 ${WORKDIR}/mosquitto ${D}/${sysconfdir}/procman.d/
}


FILES_${PN} += "${sysconfdir}/mosquitto \
               ${sysconfdir}/procman.d/mosquitto \
	       ${sysconfdir}/init.d/mosquitto.init \
"
