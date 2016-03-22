###
### Change MULTIPLEXORD_PREPEND everything you touch this script
###
MULTIPLEXORD_PREPEND = "-1"

MULTIPLEXORD_REVISION ?= "${DISTRO_PR}"
PKGR_${PN}-multiplexerd = "${PR}${MULTIPLEXORD_PREPEND}${MULTIPLEXORD_REVISION}"

do_install_append () {
	install -d ${D}/${INSTALL_DIR}/multiplexerd/
	${RSYNC_CMD} ${S}/code/multiplexerd/ ${D}/${INSTALL_DIR}/multiplexerd/
	chmod +x ${D}/${INSTALL_DIR}/multiplexerd/multiplexerd.pyo
}

FILES_${PN}-multiplexerd = "\
	${INSTALL_DIR}/multiplexerd/*	\
	${sysconfdir}/init.d/_multiplexerd		\
	${sysconfdir}/procman.d/multiplexerd		\
	"


