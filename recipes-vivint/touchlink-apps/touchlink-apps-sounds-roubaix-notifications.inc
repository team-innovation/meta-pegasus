###
### Change SOUND_ROUBAIX_NOTI_PREPEND everytime you touch this script
###
SOUND_ROUBAIX_NOTI_PREPEND = "-1"

SOUND_ROUBAIX_NOTI_REVISION ?= "${DISTRO_PR}"
PKGR_${PN}-sound-roubaix-notifications = "${PR}${SOUND_ROUBAIX_NOTI_PREPEND}${SOUND_ROUBAIX_NOTI_REVISION}"

do_install_append() {
	install -d ${D}/${INSTALL_DIR}/sounds/roubaix/notifications
    ${RSYNC_CMD} ${S}/sounds/roubaix/notifications/ ${D}/${INSTALL_DIR}/sounds/roubaix/notifications
}

FILES_${PN}-sound-roubaix-notifications = "\
       ${INSTALL_DIR}/sounds/roubaix/notifications/*"

             
