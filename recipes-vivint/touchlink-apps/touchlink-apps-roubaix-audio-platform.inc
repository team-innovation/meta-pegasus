###
### Change ROUBAIX_AUDIO_PLATFORM_PREPEND everytime you touch this script
###
ROUBAIX_AUDIO_PLATFORM_PREPEND = "-1"

ROUBAIX_AUDIO_PLATFORM_REVISION ?= "${DISTRO_PR}"
PKGR_${PN}-roubaix-audio-platform = "${PR}${ROUBAIX_AUDIO_PLATFORM_PREPEND}${ROUBAIX_AUDIO_PLATFORM_REVISION}"

FILES_${PN}-roubaix-audio-platform = "\
	${INSTALL_DIR}/roubaix/audio_platform/* \
	"
