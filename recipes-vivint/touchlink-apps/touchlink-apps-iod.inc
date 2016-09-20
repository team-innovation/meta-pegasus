###
### Change IOD_PREPEND everything you touch this script
###
IOD_PREPEND = "-3"

IOD_REVISION ?= "${DISTRO_PR}"
PKGR_${PN}-iod = "${PR}${IOD_PREPEND}${IOD_REVISION}"

INITSCRIPT_PACKAGES += "${PN}-iod"
INITSCRIPT_NAME_${PN}-iod = "iod"
INITSCRIPT_PARAMS_${PN}-iod = "start 25 2 3 4 5 ."

do_install_append () {
	install -d ${D}/${INSTALL_DIR}/iod/
        ${RSYNC_CMD} ${S}/code/iod/ ${D}/${INSTALL_DIR}/iod/
        chmod +x ${D}/${INSTALL_DIR}/iod/iod.pyo
}

FILES_${PN}-iod = "\
       ${INSTALL_DIR}/iod/*   \
	${sysconfdir}/init.d/iod		\
	${sysconfdir}/procman.d/iod		\
       "

