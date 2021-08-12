FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

PR = "r6"

SYSTEMD_AUTO_ENABLE_${PN}-sshd = "disable"
