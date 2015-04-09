###
### Change SOUND_WAV_AD_PREPEND everytime you touch this script
###
SOUND_WAV_AD_PREPEND = "-1"

SOUND_WAV_AD_REVISION ?= "${DISTRO_PR}"
PKGR_${PN}-sound-wav-ad = "${PR}${SOUND_WAV_AD_PREPEND}${SOUND_WAV_AD_REVISION}"

do_install_append() {
	install -d ${D}/${INSTALL_DIR}/sounds/wav/arming_disarming
    ${RSYNC_CMD} ${S}/sounds/wav/arming_disarming/ ${D}/${INSTALL_DIR}/sounds/wav/arming_disarming
}

FILES_${PN}-sound-wav-ad = "\
       ${INSTALL_DIR}/sounds/wav/arming_disarming/*"

             
