###
### Change SOUND_ROUBAIX_SEL_PREPEND everytime you touch this script
###
SOUND_ROUBAIX_SEL_PREPEND = "-1"

SOUND_ROUBAIX_SEL_REVISION ?= "${DISTRO_PR}"
PKGR_${PN}-sound-roubaix-select = "${PR}${SOUND_ROUBAIX_SEL_PREPEND}${SOUND_ROUBAIX_SEL_REVISION}"

do_install_append() {
	install -d ${D}/${INSTALL_DIR}/sounds/roubaix/select
    ${RSYNC_CMD} ${S}/sounds/roubaix/select/ ${D}/${INSTALL_DIR}/sounds/roubaix/select
}

FILES_${PN}-sound-roubaix-select = "\
       ${INSTALL_DIR}/sounds/roubaix/select/*"

             
