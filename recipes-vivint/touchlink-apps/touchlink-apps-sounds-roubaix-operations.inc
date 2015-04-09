###
### Change SOUND_ROUBAIX_OPER_PREPEND everytime you touch this script
###
SOUND_ROUBAIX_OPER_PREPEND = "-1"

SOUND_ROUBAIX_OPER_REVISION ?= "${DISTRO_PR}"
PKGR_${PN}-sound-roubaix-operations = "${PR}${SOUND_ROUBAIX_OPER_PREPEND}${SOUND_ROUBAIX_OPER_REVISION}"

do_install_append() {
	install -d ${D}/${INSTALL_DIR}/sounds/roubaix/operations
    ${RSYNC_CMD} ${S}/sounds/roubaix/operations/ ${D}/${INSTALL_DIR}/sounds/roubaix/operations
}

FILES_${PN}-sound-roubaix-operations = "\
       ${INSTALL_DIR}/sounds/roubaix/operations/*"

             
