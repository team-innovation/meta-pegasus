###
### Change ROUBAIX_VIEWS_PREPEND everytime you touch this script
###
ROUBAIX_VIEWS_PREPEND = "-1"

ROUBAIX_VIEWS_REVISION ?= "${DISTRO_PR}"
PKGR_${PN}-roubaix-views = "${PR}${ROUBAIX_VIEWS_PREPEND}${ROUBAIX_VIEWS_REVISION}"

FILES_${PN}-roubaix-views = "\
	${INSTALL_DIR}/roubaix/views/* \
	"
