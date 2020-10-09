FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
PR = "6"

SRC_URI += "file://avahi-daemon.conf"

do_install_append () {
    install -m 0644 ${WORKDIR}/avahi-daemon.conf ${D}${sysconfdir}/avahi
}

FILES_${PN} += "${sysconfdir}/avahi/avahi-daemon.conf"

