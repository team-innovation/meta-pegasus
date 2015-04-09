###
### Change FRAMEWORK_PREPEND everything you touch this script
###
QML_FRWRK_CTRL_PREPEND = "-1"

QML_FRWRK_CTRL_REVISION ?= "${DISTRO_PR}"
PKGR_${PN}-framework-controls = "${PR}${QML_FRWRK_CTRL_PREPEND}${QML_FRWRK_CTRL_REVISION}"


FILES_${PN}-qml-framework-controls = "\
	${INSTALL_DIR}/framework/qml_framework/controls/*	\
    "
