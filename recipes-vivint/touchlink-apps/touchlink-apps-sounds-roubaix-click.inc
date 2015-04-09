###
### Change SOUND_ROUBAIX_CLICK_PREPEND everytime you touch this script
###
SOUND_ROUBAIX_CLICK_PREPEND = "-1"

SOUND_ROUBAIX_CLICK_REVISION ?= "${DISTRO_PR}"
PKGR_${PN}-sound-roubaix-click = "${PR}${SOUND_ROUBAIX_CLICK_PREPEND}${SOUND_ROUBAIX_CLICK_REVISION}"

do_install_append() {
	install -d ${D}/${INSTALL_DIR}/sounds/roubaix/click
    ${RSYNC_CMD} ${S}/sounds/roubaix/click/ ${D}/${INSTALL_DIR}/sounds/roubaix/click
}

FILES_${PN}-sound-roubaix-click = "\
       ${INSTALL_DIR}/sounds/roubaix/click/*"

             
