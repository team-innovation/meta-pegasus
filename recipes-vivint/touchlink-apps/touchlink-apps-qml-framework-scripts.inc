###
### Change FRAMEWORK_PREPEND everything you touch this script
###
QML_FRWRK_SCRIPTS_PREPEND = "-1"

QML_FRWRK_SCRIPTS_REVISION ?= "${DISTRO_PR}"
PKGR_${PN}-framework-scripts = "${PR}${QML_FRWRK_SCRIPTS_PREPEND}${QML_FRWRK_SCRIPTS_REVISION}"


FILES_${PN}-qml-framework-scripts = "\
	${INSTALL_DIR}/framework/qml_framework/scripts/*	\
    "
