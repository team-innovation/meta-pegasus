###
### Change SOUND_ROUBAIX_SEC_PREPEND everytime you touch this script
###
SOUND_ROUBAIX_SEC_PREPEND = "-1"

SOUND_ROUBAIX_SEC_REVISION ?= "${DISTRO_PR}"
PKGR_${PN}-sound-roubaix-security = "${PR}${SOUND_ROUBAIX_SEC_PREPEND}${SOUND_ROUBAIX_SEC_REVISION}"

do_install_append() {
	install -d ${D}/${INSTALL_DIR}/sounds/roubaix/security
    ${RSYNC_CMD} ${S}/sounds/roubaix/security/ ${D}/${INSTALL_DIR}/sounds/roubaix/security
}

FILES_${PN}-sound-roubaix-security = "\
       ${INSTALL_DIR}/sounds/roubaix/security/*"

             
