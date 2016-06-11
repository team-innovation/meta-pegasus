###
### Change ROUBAIX_IMG_PREPEND everytime you touch this script
###
ROUBAIX_IMG_COIN_PREPEND = "-1"
ROUBAIX_IMG_CTRL_PREPEND = "-1"
ROUBAIX_IMG_PNG_PREPEND = "-1"
ROUBAIX_IMG_RULES_PREPEND = "-1"
ROUBAIX_IMG_SHARED_PREPEND = "-1"
ROUBAIX_IMG_SVG_PREPEND = "-1"
ROUBAIX_IMG_TOOLBOX_PREPEND = "-1"
ROUBAIX_IMG_VIDEO_PREPEND = "-1"
ROUBAIX_IMG_WP_PREPEND = "-1"

### Themes
ROUBAIX_TH_ALA_PREPEND = "-1"
ROUBAIX_TH_AQUA_PREPEND = "-1"
ROUBAIX_TH_FOREST_PREPEND = "-1"

ROUBAIX_IMG_COIN_REVISION ?= "${DISTRO_PR}"
PKGR_${PN}-roubaix-images-coin = "${PR}${ROUBAIX_IMG_COIN_PREPEND}${ROUBAIX_IMG_COIN_REVISION}"

ROUBAIX_IMG_CTRL_REVISION ?= "${DISTRO_PR}"
PKGR_${PN}-roubaix-images-controls = "${PR}${ROUBAIX_IMG_CTRL_PREPEND}${ROUBAIX_IMG_CTRL_REVISION}"

ROUBAIX_IMG_PNG_REVISION ?= "${DISTRO_PR}"
PKGR_${PN}-roubaix-images-png = "${PR}${ROUBAIX_IMG_PNG_PREPEND}${ROUBAIX_IMG_PNG_REVISION}"

ROUBAIX_IMG_RULES_REVISION ?= "${DISTRO_PR}"
PKGR_${PN}-roubaix-images-rules = "${PR}${ROUBAIX_IMG_RULES_PREPEND}${ROUBAIX_IMG_RULES_REVISION}"

ROUBAIX_IMG_SHARED_REVISION ?= "${DISTRO_PR}"
PKGR_${PN}-roubaix-images-shared = "${PR}${ROUBAIX_IMG_SHARED_PREPEND}${ROUBAIX_IMG_SHARED_REVISION}"

ROUBAIX_IMG_SVG_REVISION ?= "${DISTRO_PR}"
PKGR_${PN}-roubaix-images-svg = "${PR}${ROUBAIX_IMG_SVG_PREPEND}${ROUBAIX_IMG_SVG_REVISION}"

ROUBAIX_IMG_TOOLBOX_REVISION ?= "${DISTRO_PR}"
PKGR_${PN}-roubaix-images-toolbox = "${PR}${ROUBAIX_IMG_TOOLBOX_PREPEND}${ROUBAIX_IMG_TOOLBOX_REVISION}"

ROUBAIX_IMG_VIDEO_REVISION ?= "${DISTRO_PR}"
PKGR_${PN}-roubaix-images-video = "${PR}${ROUBAIX_IMG_VIDEO_PREPEND}${ROUBAIX_IMG_VIDEO_REVISION}"

ROUBAIX_IMG_WP_REVISION ?= "${DISTRO_PR}"
PKGR_${PN}-roubaix-images-wallpapers = "${PR}${ROUBAIX_IMG_WP_PREPEND}${ROUBAIX_IMG_WP_REVISION}"

ROUBAIX_TH_ALA_REVISION ?= "${DISTRO_PR}"
PKGR_${PN}-roubaix-themes-alabaster = "${PR}${ROUBAIX_TH_ALA_PREPEND}${ROUBAIX_TH_ALA_REVISION}"

ROUBAIX_TH_AQUA_REVISION ?= "${DISTRO_PR}"
PKGR_${PN}-roubaix-themes-aqua = "${PR}${ROUBAIX_TH_AQUA_PREPEND}${ROUBAIX_TH_AQUA_REVISION}"

ROUBAIX_TH_FOREST_REVISION ?= "${DISTRO_PR}"
PKGR_${PN}-roubaix-themes-forest = "${PR}${ROUBAIX_TH_FOREST_PREPEND}${ROUBAIX_TH_FOREST_REVISION}"

FILES_${PN}-roubaix-images-coin = "\
	${INSTALL_DIR}/roubaix/images/coin/* \
	"

FILES_${PN}-roubaix-images-controls = "\
	${INSTALL_DIR}/roubaix/images/controls/* \
	"

FILES_${PN}-roubaix-images-png = "\
	${INSTALL_DIR}/roubaix/images/png/* \
	"

FILES_${PN}-roubaix-images-rules = "\
	${INSTALL_DIR}/roubaix/images/rules/* \
	"

FILES_${PN}-roubaix-images-shared = "\
	${INSTALL_DIR}/roubaix/images/shared/* \
	"

FILES_${PN}-roubaix-images-svg= "\
    ${INSTALL_DIR}/roubaix/images/svg/* \
	    "

FILES_${PN}-roubaix-images-toolbox = "\
	${INSTALL_DIR}/roubaix/images/toolbox/* \
	"

FILES_${PN}-roubaix-images-video = "\
	${INSTALL_DIR}/roubaix/images/video/* \
	"

FILES_${PN}-roubaix-images-wallpapers = "\
	${INSTALL_DIR}/roubaix/images/wallpapers/* \
	"

FILES_${PN}-roubaix-themes-alabaster = "\
	${INSTALL_DIR}/roubaix/images/themes/alabaster/* \
	"

FILES_${PN}-roubaix-themes-aqua = "\
	${INSTALL_DIR}/roubaix/images/themes/aqua/* \
	"

FILES_${PN}-roubaix-themes-forest = "\
	${INSTALL_DIR}/roubaix/images/themes/forest/* \
	"

