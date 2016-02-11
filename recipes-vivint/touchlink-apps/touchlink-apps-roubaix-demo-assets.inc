###
### Change ROUBAIX_DEMO_ASSETS everytime you touch this script
###
ROUBAIX_DEMO_ASSETS_PREPEND = "-1"

ROUBAIX_DEMO_ASSETS_REVISION ?= "${DISTRO_PR}"
PKGR_${PN}-roubaix-demo-assets = "${PR}${ROUBAIX_DEMO_ASSETS_PREPEND}${ROUBAIX_DEMO_ASSETS_REVISION}"

FILES_${PN}-roubaix-demo-assets = "\
	${INSTALL_DIR}/roubaix/demo_assets/* \
	"
