###
### Change SOUND_ROUBAIX_LOCKS_PREPEND everytime you touch this script
###
SOUND_ROUBAIX_LOCKS_PREPEND = "-1"

SOUND_ROUBAIX_LOCKS_REVISION ?= "${DISTRO_PR}"
PKGR_${PN}-sound-roubaix-locks = "${PR}${SOUND_ROUBAIX_LOCKS_PREPEND}${SOUND_ROUBAIX_LOCKS_REVISION}"

do_install_append() {
	install -d ${D}/${INSTALL_DIR}/sounds/roubaix/locks
    ${RSYNC_CMD} ${S}/sounds/roubaix/locks/ ${D}/${INSTALL_DIR}/sounds/roubaix/locks
}

FILES_${PN}-sound-roubaix-locks = "\
       ${INSTALL_DIR}/sounds/roubaix/locks/*"

             
