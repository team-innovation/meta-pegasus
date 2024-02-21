FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI += "file://ntp.keys"

do_install_append() {
   install -m 644 ${WORKDIR}/ntp.keys ${D}${sysconfdir}
}

FILES_${PN} += "${sysconfdir}/ntp.keys"
