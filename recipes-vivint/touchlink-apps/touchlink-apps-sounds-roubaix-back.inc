###
### Change SOUND_ROUBAIX_BACK_PREPEND everytime you touch this script
###
SOUND_ROUBAIX_BACK_PREPEND = "-1"

SOUND_ROUBAIX_BACK_REVISION ?= "${DISTRO_PR}"
PKGR_${PN}-sound-roubaix-back = "${PR}${SOUND_ROUBAIX_BACK_PREPEND}${SOUND_ROUBAIX_BACK_REVISION}"

do_install_append() {
	install -d ${D}/${INSTALL_DIR}/sounds/roubaix/back
    ${RSYNC_CMD} ${S}/sounds/roubaix/back/ ${D}/${INSTALL_DIR}/sounds/roubaix/back
}

FILES_${PN}-sound-roubaix-back = "\
       ${INSTALL_DIR}/sounds/roubaix/back/*"

             
