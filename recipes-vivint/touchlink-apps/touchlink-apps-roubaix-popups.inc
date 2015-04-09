###
### Change ROUBAIX_POPUPS_PREPEND everytime you touch this script
###
ROUBAIX_POPUPS_PREPEND = "-1"

ROUBAIX_POPUPS_REVISION ?= "${DISTRO_PR}"
PKGR_${PN}-roubaix-popups = "${PR}${ROUBAIX_POPUPS_PREPEND}${ROUBAIX_POPUPS_REVISION}"

FILES_${PN}-roubaix-popups = "\
	${INSTALL_DIR}/roubaix/popups/* \
	"
