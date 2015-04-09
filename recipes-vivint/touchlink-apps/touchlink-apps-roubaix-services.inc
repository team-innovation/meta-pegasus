###
### Change ROUBAIX_SERVICES_PREPEND everytime you touch this script
###
ROUBAIX_SERVICES_PREPEND = "-1"

ROUBAIX_SERVICES_REVISION ?= "${DISTRO_PR}"
PKGR_${PN}-roubaix-services = "${PR}${ROUBAIX_SERVICES_PREPEND}${ROUBAIX_SERVICES_REVISION}"

FILES_${PN}-roubaix-services = "\
	${INSTALL_DIR}/roubaix/services/* \
	"
