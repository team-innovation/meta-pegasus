###
### Change ROUBAIX_SCRIPTS_PREPEND everytime you touch this script
###
ROUBAIX_SCRIPTS_PREPEND = "-1"

ROUBAIX_SCRIPTS_REVISION ?= "${DISTRO_PR}"
PKGR_${PN}-roubaix-scripts = "${PR}${ROUBAIX_SCRIPTS_PREPEND}${ROUBAIX_SCRIPTS_REVISION}"

FILES_${PN}-roubaix-scripts = "\
	${INSTALL_DIR}/roubaix/scripts/* \
	"
