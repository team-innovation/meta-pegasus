###
### Change ROUBAIX_POPUPS_PREPEND everytime you touch this script
###
ROUBAIX_POPUPS_PREPEND = "-1"

ROUBAIX_POPUPS_REVISION ?= "${DISTRO_PR}"
PKGR_${PN}-roubaix-gunk = "${PR}${ROUBAIX_POPUPS_PREPEND}${ROUBAIX_POPUPS_REVISION}"

FILES_${PN}-roubaix-gunk = "\
	${INSTALL_DIR}/roubaix/precommission_data.json \
	${INSTALL_DIR}/roubaix/roubaix.qmlproject \
	"
