###
### Change FRAMEWORK_PREPEND everything you touch this script
###
QML_FRWRK_POPUPS_PREPEND = "-1"

QML_FRWRK_POPUPS_REVISION ?= "${DISTRO_PR}"
PKGR_${PN}-framework-popups = "${PR}${QML_FRWRK_POPUPS_PREPEND}${QML_FRWRK_POPUPS_REVISION}"


FILES_${PN}-qml-framework-popups = "\
	${INSTALL_DIR}/framework/qml_framework/popups/*	\
    "
