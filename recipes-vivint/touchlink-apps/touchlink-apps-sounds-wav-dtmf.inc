###
### Change SOUND_WAV_DTMF_PREPEND everytime you touch this script
###
SOUND_WAV_DTMF_PREPEND = "-1"

SOUND_WAV_DTMF_REVISION ?= "${DISTRO_PR}"
PKGR_${PN}-sound-wav-dtmf = "${PR}${SOUND_WAV_DTMF_PREPEND}${SOUND_WAV_DTMF_REVISION}"

do_install_append() {
	install -d ${D}/${INSTALL_DIR}/sounds/wav/dtmf_tones
    ${RSYNC_CMD} ${S}/sounds/wav/dtmf_tones/ ${D}/${INSTALL_DIR}/sounds/wav/dtmf_tones
}

FILES_${PN}-sound-wav-dtmf = "\
       ${INSTALL_DIR}/sounds/wav/dtmf_tones/*"

             
