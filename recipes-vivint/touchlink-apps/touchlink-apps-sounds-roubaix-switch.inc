###
### Change SOUND_ROUBAIX_SWITCH_PREPEND everytime you touch this script
###
SOUND_ROUBAIX_SWITCH_PREPEND = "-1"

SOUND_ROUBAIX_SWITCH_REVISION ?= "${DISTRO_PR}"
PKGR_${PN}-sound-roubaix-switch = "${PR}${SOUND_ROUBAIX_SWITCH_PREPEND}${SOUND_ROUBAIX_SWITCH_REVISION}"

do_install_append() {
	install -d ${D}/${INSTALL_DIR}/sounds/roubaix/switch
    ${RSYNC_CMD} ${S}/sounds/roubaix/switch/ ${D}/${INSTALL_DIR}/sounds/roubaix/switch
}

FILES_${PN}-sound-roubaix-switch = "\
       ${INSTALL_DIR}/sounds/roubaix/switch/*"

             
