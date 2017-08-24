###
### Change SOUND_WAV_CHIMES_PREPEND everytime you touch this script
###
SOUND_WAV_CHIMES_PREPEND = "-3"

SOUND_WAV_CHIMES_REVISION ?= "${DISTRO_PR}"
PKGR_${PN}-sound-wav-chimes = "${PR}${SOUND_WAV_CHIMES_PREPEND}${SOUND_WAV_CHIMES_REVISION}"

do_install_append() {
	install -d ${D}/${INSTALL_DIR}/sounds/wav/chimes
    ${RSYNC_CMD} ${S}/sounds/wav/chimes/ ${D}/${INSTALL_DIR}/sounds/wav/chimes
    ln -s ${D}/${INSTALL_DIR}/sounds/wav ${D}/${INSTALL_DIR}/sounds/roubaix
}

FILES_${PN}-sound-wav-chimes = "\
       ${INSTALL_DIR}/sounds/wav/chimes/*"

             
