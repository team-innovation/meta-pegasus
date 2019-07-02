###
### Change MODEMD_PREPEND everytime you touch this script
###
MODEMD_PREPEND = "-5"

MODEMD_REVISION ?= "${DISTRO_PR}"
PKGR_${PN}-modemd = "${PR}${MODEMD_PREPEND}${MODEMD_REVISION}"

INITSCRIPT_PACKAGES += "${PN}-modemd"
INITSCRIPT_NAME_${PN}-modemd = "modemd"
INITSCRIPT_PARAMS_${PN}-modemd = "start 26 2 3 4 5 ."

do_install_append () {
	install -d ${D}/${INSTALL_DIR}/modemd/
	${RSYNC_CMD} ${S}/code/modemd/ ${D}/${INSTALL_DIR}/modemd/
	chmod +x ${D}/${INSTALL_DIR}/modemd/modemd.pyc
}

pkg_postinst_ontarget_${PN} () {

    /usr/local/bin/mfr_modem_test.py

}

FILES_${PN}-modemd-proxies = "\
		${INSTALL_DIR}/modemd/proxies/* \
		"

FILES_${PN}-modemd = "\
	${INSTALL_DIR}/modemd/*	\
	${sysconfdir}/init.d/modemd		\
	${sysconfdir}/procman.d/modemd		\
	"


