###
### Change NFCD_PREPEND everything you touch this script
###
NFCD_PREPEND = "-1"

NFCD_REVISION ?= "${DISTRO_PR}"
PKGR_${PN}-nfcd = "${PR}${NFCD_PREPEND}${NFCD_REVISION}"

INITSCRIPT_PACKAGES += "${PN}-nfcd"
INITSCRIPT_NAME_${PN}-nfcd = "nfcd"
INITSCRIPT_PARAMS_${PN}-nfcd = "start 26 2 3 4 5 ."

do_install_append () {
	install -d ${D}/${INSTALL_DIR}/nfcd/
	${RSYNC_CMD} ${S}/code/nfcd/ ${D}/${INSTALL_DIR}/nfcd/
	chmod +x ${D}/${INSTALL_DIR}/nfcd/nfcd.pyo
}

FILES_${PN}-nfcd = "\
	${INSTALL_DIR}/nfcd/*	\
	${sysconfdir}/init.d/nfcd		\
	${sysconfdir}/procman.d/nfcd		\
	"


