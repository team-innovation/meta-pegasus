###
### Change IOD_PREPEND everything you touch this script
###
IOD_PREPEND = "-1"

IOD_REVISION ?= "${DISTRO_PR}"
PKGR_${PN}-iod = "${PR}${IOD_PREPEND}${IOD_REVISION}"

INITSCRIPT_PACKAGES += "${PN}-iod"
INITSCRIPT_NAME_${PN}-iod = "iod"
INITSCRIPT_PARAMS_${PN}-iod = "defaults 2 17"

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

