###
### Change RFONT_PREPEND everything you touch this script
###
RFONT_PREPEND = "-3"

RFONT_REVISION ?= "${DISTRO_PR}"
PKGR_${PN}-roubaix-fonts = "${PR}${RFONT_PREPEND}${RFONT_REVISION}"

do_install_append (){
	install -d ${D}/usr/lib/fonts
        ${RSYNC_CMD} ${S}/code/framework/qml_framework/fonts/ ${D}/usr/lib/fonts
}


FILES_${PN}-roubaix-fonts = "\
	/usr/lib/fonts/* \
	${INSTALL_DIR}/framework/qml_framework/fonts/* \
    "

