###
### Change ROUBAIX_FWRK_PREPEND everytime you touch this script
###
ROUBAIX_FWRK_PREPEND = "-1"

ROUBAIX_FWRK_REVISION ?= "${DISTRO_PR}"
PKGR_${PN}-roubaix-framework = "${PR}${ROUBAIX_FWRK_PREPEND}${ROUBAIX_FWRK_REVISION}"

FILES_${PN}-roubaix-framework = "\
	${INSTALL_DIR}/roubaix/framework/* \
	"
