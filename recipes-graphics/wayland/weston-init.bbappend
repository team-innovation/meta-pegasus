FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI_append = " file://weston.config"

PR = "3"

do_install_append() {
	install -Dm0755 ${WORKDIR}/weston.config ${D}${sysconfdir}/default/weston
}
