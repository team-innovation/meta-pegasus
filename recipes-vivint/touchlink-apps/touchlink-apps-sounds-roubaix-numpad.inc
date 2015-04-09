###
### Change SOUND_ROUBAIX_NUMPAD_PREPEND everytime you touch this script
###
SOUND_ROUBAIX_NUMPAD_PREPEND = "-1"

SOUND_ROUBAIX_NUMPAD_REVISION ?= "${DISTRO_PR}"
PKGR_${PN}-sound-roubaix-numpad = "${PR}${SOUND_ROUBAIX_NUMPAD_PREPEND}${SOUND_ROUBAIX_NUMPAD_REVISION}"

do_install_append() {
	install -d ${D}/${INSTALL_DIR}/sounds/roubaix/numpad
    ${RSYNC_CMD} ${S}/sounds/roubaix/numpad/ ${D}/${INSTALL_DIR}/sounds/roubaix/numpad
}

FILES_${PN}-sound-roubaix-numpad = "\
       ${INSTALL_DIR}/sounds/roubaix/numpad/*"

             
