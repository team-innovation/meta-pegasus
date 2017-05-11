###
### Change FRAMEWORK_PREPEND everything you touch this script
###
QML_FRAMEWORK_PREPEND = "-4"

QML_FRAMEWORK_REVISION ?= "${DISTRO_PR}"
PKGR_${PN}-framework = "${PR}${QML_FRAMEWORK_PREPEND}${QML_FRAMEWORK_REVISION}"

do_install_append () {
    install -d ${D}/${INSTALL_DIR}/framework/
	${RSYNC_CMD} ${S}/code/framework/qml_framework/ ${D}/${INSTALL_DIR}/framework/qml_framework

	find ${D}/${INSTALL_DIR}/framework -name proxies -prune -o -name *.py -print | xargs rm -f
}

	
FILES_${PN}-qml-framework = "\
	${INSTALL_DIR}/framework/qml_framework/qml_framework.qmlproject	\
	${INSTALL_DIR}/framework/qml_framework/qml_framework.qml	\
	${INSTALL_DIR}/framework/qml_framework/style/*	\
	${INSTALL_DIR}/framework/qml_framework/time/*	\
    ${INSTALL_DIR}/framework/qml_framework/fonts/* \
    "
