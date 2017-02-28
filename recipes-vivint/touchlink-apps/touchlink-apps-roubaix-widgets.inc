###
### Change ROUBAIX_WIDGETS_PREPEND everytime you touch this script
###
ROUBAIX_WIDGETS_PREPEND = "-1"

ROUBAIX_WIDGETS_REVISION ?= "${DISTRO_PR}"
PKGR_${PN}-roubaix-widgets = "${PR}${ROUBAIX_WIDGETS_PREPEND}${ROUBAIX_WIDGETS_REVISION}"

FILES_${PN}-roubaix-widgets = "\
	${INSTALL_DIR}/roubaix/widgets/* \
	"
