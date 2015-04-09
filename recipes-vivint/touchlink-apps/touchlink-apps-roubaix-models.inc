###
### Change ROUBAIX_MODELS_PREPEND everytime you touch this script
###
ROUBAIX_MODELS_PREPEND = "-1"

ROUBAIX_MODELS_REVISION ?= "${DISTRO_PR}"
PKGR_${PN}-roubaix-models = "${PR}${ROUBAIX_MODELS_PREPEND}${ROUBAIX_MODELS_REVISION}"

FILES_${PN}-roubaix-models = "\
	${INSTALL_DIR}/roubaix/models/* \
	"
