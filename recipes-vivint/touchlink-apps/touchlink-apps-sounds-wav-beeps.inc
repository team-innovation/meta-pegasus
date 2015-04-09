###
### Change SOUND_WAV_BEEPS_PREPEND everytime you touch this script
###
SOUND_WAV_BEEPS_PREPEND = "-1"

SOUND_WAV_BEEPS_REVISION ?= "${DISTRO_PR}"
PKGR_${PN}-sound-wav-beeps = "${PR}${SOUND_WAV_BEEPS_PREPEND}${SOUND_WAV_BEEPS_REVISION}"

do_install_append() {
	install -d ${D}/${INSTALL_DIR}/sounds/wav/beeps
    ${RSYNC_CMD} ${S}/sounds/wav/beeps/ ${D}/${INSTALL_DIR}/sounds/wav/beeps
}

FILES_${PN}-sound-wav-beeps = "\
       ${INSTALL_DIR}/sounds/wav/beeps/*"

             
