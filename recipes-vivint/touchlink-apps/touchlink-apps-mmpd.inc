###
### Change MMPD_PREPEND everything you touch this script
###
MMPD_PREPEND = "-1"

MMPD_REVISION ?= "${DISTRO_PR}"
PKGR_${PN}-mmpd = "${PR}${MMPD_PREPEND}${MMPD_REVISION}"

do_install_append () {
	install -d ${D}/${INSTALL_DIR}/mmpd/
	${RSYNC_CMD} ${S}/code/nfcd/ ${D}/${INSTALL_DIR}/mmpd/
	chmod +x ${D}/${INSTALL_DIR}/mmpd/mmpd.pyo
}

FILES_${PN}-mmpd = "\
	${INSTALL_DIR}/mmpd/*	\
	${sysconfdir}/init.d/mmpd		\
	${sysconfdir}/procman.d/mmpd		\
	"


