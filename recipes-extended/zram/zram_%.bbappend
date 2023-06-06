FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

PR = "r1"

SRC_URI += "file://zram \
	   file://init \
	   file://zram-swap-init \
	   file://zram-swap.service"

do_install_append () {
    install -d ${D}${sysconfdir}/default
    install -m 0644 ${WORKDIR}/zram ${D}${sysconfdir}/default
}
