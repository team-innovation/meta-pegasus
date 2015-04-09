###
### Change SOUND_ROUBAIX_DIALOG_PREPEND everytime you touch this script
###
SOUND_ROUBAIX_DIALOG_PREPEND = "-1"

SOUND_ROUBAIX_DIALOG_REVISION ?= "${DISTRO_PR}"
PKGR_${PN}-sound-roubaix-dialog = "${PR}${SOUND_ROUBAIX_DIALOG_PREPEND}${SOUND_ROUBAIX_DIALOG_REVISION}"

do_install_append() {
	install -d ${D}/${INSTALL_DIR}/sounds/roubaix/dialog
    ${RSYNC_CMD} ${S}/sounds/roubaix/dialog/ ${D}/${INSTALL_DIR}/sounds/roubaix/dialog
}

FILES_${PN}-sound-roubaix-dialog = "\
       ${INSTALL_DIR}/sounds/roubaix/dialog/*"

             
