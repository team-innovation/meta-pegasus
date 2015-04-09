###
### Change D345_PREPEND everything you touch this script
###
D345_PREPEND = "-1"

D345_REVISION ?= "${DISTRO_PR}"
PKGR_${PN}-345d = "${PR}${D345_PREPEND}${D345_REVISION}"

do_install_append () {
	install -d ${D}/${INSTALL_DIR}/345d/
	${RSYNC_CMD} ${S}/code/345d/ ${D}/${INSTALL_DIR}/345d/
	chmod +x ${D}/${INSTALL_DIR}/345d/345d.pyo
}

FILES_${PN}-345d = "\
	${INSTALL_DIR}/345d/*	\
	${sysconfdir}/init.d/_345d		\
	${sysconfdir}/procman.d/345d		\
	"


