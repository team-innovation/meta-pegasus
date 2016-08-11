FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI = "file://sshd_not_to_be_run"
PR = "1"

do_install_append () {
    install -d ${D}/${sysconfdir}/ssh
    install -m 644  ${WORKDIR}/sshd_not_to_be_run ${D}/${sysconfdir}/ssh/
}

FILES_${PN}_append = " ${sysconfdir}/ssh/sshd_not_to_be_run"
