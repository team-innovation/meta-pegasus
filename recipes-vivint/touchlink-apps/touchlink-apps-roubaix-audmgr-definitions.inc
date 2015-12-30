###
### Change ROUBAIX_AUDMGR_DEFINITIONS_PREPEND everytime you touch this script
###
ROUBAIX_AUDMGR_DEFINITIONS_PREPEND = "-1"

ROUBAIX_AUDMGR_DEFINITIONS_REVISION ?= "${DISTRO_PR}"
PKGR_${PN}-roubaix-audmgr-definitions = "${PR}${ROUBAIX_AUDMGR_DEFINITIONS_PREPEND}${ROUBAIX_AUDMGR_DEFINITIONS_REVISION}"

FILES_${PN}-roubaix-audmgr-definitions = "\
	${INSTALL_DIR}/roubaix/audmgr_definitions/* \
	"
