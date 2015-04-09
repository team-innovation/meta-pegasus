###
### Change SOUND_WAV_PAUSES_PREPEND everytime you touch this script
###
SOUND_WAV_PAUSES_PREPEND = "-1"

SOUND_WAV_PAUSES_REVISION ?= "${DISTRO_PR}"
PKGR_${PN}-sound-wav-pauses = "${PR}${SOUND_WAV_PAUSES_PREPEND}${SOUND_WAV_PAUSES_REVISION}"

do_install_append() {
	install -d ${D}/${INSTALL_DIR}/sounds/wav/pauses
    ${RSYNC_CMD} ${S}/sounds/wav/pauses/ ${D}/${INSTALL_DIR}/sounds/wav/pauses
}

FILES_${PN}-sound-wav-pauses = "\
       ${INSTALL_DIR}/sounds/wav/pauses/*"

             
