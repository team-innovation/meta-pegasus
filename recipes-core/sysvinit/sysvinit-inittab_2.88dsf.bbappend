FILESEXTRAPATHS_prepend := "${THISDIR}/sysvinit:"

SRC_URI_append = " file://rc_gs.S"

do_install_append() {
    install -d ${D}${sysconfdir} ${D}${sysconfdir}/init.d
    install -m 0755 ${WORKDIR}/rc_gs.S ${D}${sysconfdir}/init.d

    echo "" >> ${D}${sysconfdir}/inittab
    echo "GS::respawn:/etc/init.d/rc_gs.S" >> ${D}${sysconfdir}/inittab
}

FILES_${PN}_append = " ${sysconfdir}/init.d/rc_gs.S"
