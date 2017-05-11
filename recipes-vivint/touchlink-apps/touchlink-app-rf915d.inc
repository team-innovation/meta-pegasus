###
### Change RF915D_PREPEND everything you touch this script
###
RF915D_PREPEND = "-1"

RF915D_REVISION ?= "${DISTRO_PR}"
PKGR_${PN}-rf915d = "${PR}${RF915D_PREPEND}${RF915D_REVISION}"

do_install_append () {
	install -d ${D}/${INSTALL_DIR}/rf915d/
	${RSYNC_CMD} ${S}/code/rf915d/ ${D}/${INSTALL_DIR}/rf915d/
	chmod +x ${D}/${INSTALL_DIR}/rf915d/rf915d.pyo
}

FILES_${PN}-345d = "\
	${INSTALL_DIR}/rf915d/*	\
	${sysconfdir}/init.d/rf915d		\
	${sysconfdir}/procman.d/rf915d		\
	"


