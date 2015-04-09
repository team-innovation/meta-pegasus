###
### Change SOUND_WAV_ABS_PREPEND everytime you touch this script
###
SOUND_WAV_ABS_PREPEND = "-1"

SOUND_WAV_ABS_REVISION ?= "${DISTRO_PR}"
PKGR_${PN}-sound-wav-abs = "${PR}${SOUND_WAV_ABS_PREPEND}${SOUND_WAV_ABS_REVISION}"

do_install_append() {
	install -d ${D}/${INSTALL_DIR}/sounds/wav/alarms_buzzers_sirens
    ${RSYNC_CMD} ${S}/sounds/wav/alarms_buzzers_sirens/ ${D}/${INSTALL_DIR}/sounds/wav/alarms_buzzers_sirens
}

FILES_${PN}-sound-wav-abs = "\
       ${INSTALL_DIR}/sounds/wav/alarms_buzzers_sirens/*"

             
