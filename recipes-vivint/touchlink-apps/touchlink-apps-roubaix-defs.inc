###
### Change ROUBAIX_DEFS_PREPEND everytime you touch this script
###
ROUBAIX_DEFS_PREPEND = "-1"

ROUBAIX_DEFS_REVISION ?= "${DISTRO_PR}"
PKGR_${PN}-roubaix-defs = "${PR}${ROUBAIX_DEFS_PREPEND}${ROUBAIX_DEFS_REVISION}"

FILES_${PN}-roubaix-defs = "\
	${INSTALL_DIR}/roubaix/defs/* \
	"
