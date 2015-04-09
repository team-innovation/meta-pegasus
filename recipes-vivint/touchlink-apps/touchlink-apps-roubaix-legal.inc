###
### Change ROUBAIX_LEGAL_PREPEND everytime you touch this script
###
ROUBAIX_LEGAL_PREPEND = "-1"

ROUBAIX_LEGAL_REVISION ?= "${DISTRO_PR}"
PKGR_${PN}-roubaix-legal = "${PR}${ROUBAIX_LEGAL_PREPEND}${ROUBAIX_LEGAL_REVISION}"

FILES_${PN}-roubaix-legal = "\
	${INSTALL_DIR}/roubaix/legal/* \
	"
