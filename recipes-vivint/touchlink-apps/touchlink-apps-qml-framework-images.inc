###
### Change FRAMEWORK_PREPEND everything you touch this script
###
QML_FRWRK_IMG_PREPEND = "-1"
QML_FRWRK_IMG_CONTROLS_PREPEND = "-1"
QML_FRWRK_IMG_KEYBOARDS_PREPEND = "-1"
QML_FRWRK_IMG_SHARED_PREPEND = "-1"

QML_FRWRK_IMG_REVISION ?= "${DISTRO_PR}"
PKGR_${PN}-framework-images = "${PR}${QML_FRWRK_IMG_PREPEND}${QML_FRWRK_IMG_REVISION}"

QML_FRWRK_IMG_CONTROLS_REVISION ?= "${DISTRO_PR}"
PKGR_${PN}-framework-images-controls = "${PR}${QML_FRWRK_IMG_CONTROLS_PREPEND}${QML_FRWRK_IMG_CONTROLS_REVISION}"

QML_FRWRK_IMG_KEYBOARDS_REVISION ?= "${DISTRO_PR}"
PKGR_${PN}-framework-images-keyboards = "${PR}${QML_FRWRK_IMG_KEYBOARDS_PREPEND}${QML_FRWRK_IMG_KEYBOARDS_REVISION}"

QML_FRWRK_IMG_SHARED_REVISION ?= "${DISTRO_PR}"
PKGR_${PN}-framework-images-shared = "${PR}${QML_FRWRK_IMG_SHARED_PREPEND}${QML_FRWRK_IMG_SHARED_REVISION}"

FILES_${PN}-qml-framework-images-controls = "\
	${INSTALL_DIR}/framework/qml_framework/images/controls/*.png	\
	"
FILES_${PN}-qml-framework-images-controls-shared = "\
	${INSTALL_DIR}/framework/qml_framework/images/controls/shared/*	\
	"
