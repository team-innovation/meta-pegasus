###
### Change SOUND_WAV_VOCAB_PREPEND everytime you touch this script
###
SOUND_WAV_VOCAB_PREPEND = "-2"

SOUND_WAV_VOCAB_REVISION ?= "${DISTRO_PR}"
PKGR_${PN}-sound-wav-vocab = "${PR}${SOUND_WAV_VOCAB_PREPEND}${SOUND_WAV_VOCAB_REVISION}"

RREPLACES_${PN}-sound-wav-vocab = "${PN}-vocabulary"
RCONFLICTS_${PN}-sound-wav-vocab = "${PN}-vocabulary"

do_install_append() {
    install -d ${D}/${INSTALL_DIR}/sounds/wav/vocabulary
    ${RSYNC_CMD} ${S}/sounds/wav/vocabulary/ ${D}/${INSTALL_DIR}/sounds/wav/vocabulary
}

FILES_${PN}-sound-wav-vocab = "\
       ${INSTALL_DIR}/sounds/wav/vocabulary/*"

             
