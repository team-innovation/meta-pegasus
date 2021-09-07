FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI_append = " file://weston.config"

PR = "4"

INITSCRIPT_PACKAGES += "${PN}"
INITSCRIPT_NAME_${PN} = "weston"
INITSCRIPT_PARAMS_${PN} = "start 06 S ."

do_install_append() {
	install -Dm0755 ${WORKDIR}/weston.config ${D}${sysconfdir}/default/weston
}
