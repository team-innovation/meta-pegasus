FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

PR="r3"

SRC_URI += "file://logrotate"

do_install_append(){
    install -d ${D}${sysconfdir}/logrotate.d
    install -m 0600 ${WORKDIR}/logrotate ${D}${sysconfdir}/logrotate.d/weston
}

FILES_${PN} += "${sysconfdir}/logrotate.d/weston"
