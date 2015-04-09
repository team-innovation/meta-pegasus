###
### Change CLOUDD_PREPEND everything you touch this script
###
CLOUDD_PREPEND = "-1"

CLOUDD_REVISION ?= "${DISTRO_PR}"
PKGR_${PN}-cloudd = "${PR}${CLOUDD_PREPEND}${CLOUDD_REVISION}"

#RREPLACES_${PN}-cloudd = "${PN}-adcd"
#RCONFLICTS_${PN}-cloudd = "${PN}-adcd"

do_install_append () {
	install -d ${D}/${INSTALL_DIR}/cloudd/
	${RSYNC_CMD} ${S}/code/cloudd/ ${D}/${INSTALL_DIR}/cloudd/
	chmod +x ${D}/${INSTALL_DIR}/cloudd/cloudd.pyo
}

FILES_${PN}-cloudd = "\
	${INSTALL_DIR}/cloudd/*	\
	${sysconfdir}/init.d/cloudd		\
	${sysconfdir}/procman.d/cloudd		\
	"


