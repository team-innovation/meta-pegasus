###
### Change SOUND_WAV_CHIMES_PREPEND everytime you touch this script
###
SOUND_WAV_ABS_PREPEND = "-1"

SOUND_WAV_ABS_REVISION ?= "${DISTRO_PR}"
PKGR_${PN}-sound-wav-chimes = "${PR}${SOUND_WAV_ABS_PREPEND}${SOUND_WAV_ABS_REVISION}"

do_install_append() {
	install -d ${D}/${INSTALL_DIR}/sounds/wav/chimes
    ${RSYNC_CMD} ${S}/sounds/wav/chimes/ ${D}/${INSTALL_DIR}/sounds/wav/chimes
}

FILES_${PN}-sound-wav-chimes = "\
       ${INSTALL_DIR}/sounds/wav/chimes/*"

             
