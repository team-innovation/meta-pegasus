FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
PR = "7"

SRC_URI += "file://avahi-daemon.conf"

do_install_append () {
    install -d ${D}/${sysconfdir}/avahi/services/
    install -m 0644 ${WORKDIR}/avahi-daemon.conf ${D}${sysconfdir}/avahi
}

FILES_${PN} += "${sysconfdir}/avahi/avahi-daemon.conf"

