###
### Change SOUND_ROUBAIX_CHIMES_PREPEND everytime you touch this script
###
SOUND_ROUBAIX_CHIMES_PREPEND = "-1"

SOUND_ROUBAIX_CHIMES_REVISION ?= "${DISTRO_PR}"
PKGR_${PN}-sound-roubaix-chimes = "${PR}${SOUND_ROUBAIX_CHIMES_PREPEND}${SOUND_ROUBAIX_CHIMES_REVISION}"

do_install_append() {
	install -d ${D}/${INSTALL_DIR}/sounds/roubaix/chimes
    ${RSYNC_CMD} ${S}/sounds/roubaix/chimes/ ${D}/${INSTALL_DIR}/sounds/roubaix/chimes
}

FILES_${PN}-sound-roubaix-chimes = "\
       ${INSTALL_DIR}/sounds/roubaix/chimes/*"

             
