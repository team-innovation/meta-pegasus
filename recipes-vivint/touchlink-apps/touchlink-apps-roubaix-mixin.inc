###
### Change ROUBAIX_MIXIN_PREPEND everytime you touch this script
###
ROUBAIX_MIXIN_PREPEND = "-1"

ROUBAIX_MIXIN_REVISION ?= "${DISTRO_PR}"
PKGR_${PN}-roubaix-mixin = "${PR}${ROUBAIX_MIXIN_PREPEND}${ROUBAIX_MIXIN_REVISION}"

FILES_${PN}-roubaix-mixin = "\
	${INSTALL_DIR}/roubaix/mixin/* \
	"
