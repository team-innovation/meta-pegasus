###
### Change ROUBAIX_CONTROLS_PREPEND everytime you touch this script
###
ROUBAIX_CONTROLS_PREPEND = "-1"

ROUBAIX_CONTROLS_REVISION ?= "${DISTRO_PR}"
PKGR_${PN}-roubaix-controls = "${PR}${ROUBAIX_CONTROLS_PREPEND}${ROUBAIX_CONTROLS_REVISION}"

FILES_${PN}-roubaix-controls = "\
	${INSTALL_DIR}/roubaix/controls/* \
	"
