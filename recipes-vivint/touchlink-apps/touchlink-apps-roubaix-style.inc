###
### Change ROUBAIX_STYLE_PREPEND everytime you touch this script
###
ROUBAIX_STYLE_PREPEND = "-1"

ROUBAIX_STYLE_REVISION ?= "${DISTRO_PR}"
PKGR_${PN}-roubaix-style = "${PR}${ROUBAIX_STYLE_PREPEND}${ROUBAIX_STYLE_REVISION}"

FILES_${PN}-roubaix-style = "\
	${INSTALL_DIR}/roubaix/style/* \
	"
