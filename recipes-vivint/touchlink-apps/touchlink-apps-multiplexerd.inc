###
### Change MULTIPLEXORD_PREPEND everything you touch this script
###
MULTIPLEXORD_PREPEND = "-4"

MULTIPLEXORD_REVISION ?= "${DISTRO_PR}"
PKGR_${PN}-multiplexerd = "${PR}${MULTIPLEXORD_PREPEND}${MULTIPLEXORD_REVISION}"

INITSCRIPT_PACKAGES += "${PN}-multiplexerd"
INITSCRIPT_NAME_${PN}-multiplexerd = "multiplexerd"
INITSCRIPT_PARAMS_${PN}-multiplexerd = "start 24 2 3 4 5 ."

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


