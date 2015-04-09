###
### Change FAVICO_PREPEND everything you touch this script
###
FAVICO_PREPEND = "-2"

FAV_ICON_REVISION ?= "${DISTRO_PR}"
PKGR_${PN}-favicon = "${PR}${FAVICO_PREPEND}${FAV_ICON_REVISION}"
 
do_install_append () {

	# favicon
	install -d ${D}/${INSTALL_DIR}/images
	cp ${WORKDIR}/favicon.ico ${D}/${INSTALL_DIR}/images
}

FILES_${PN}-favicon = "\
       ${INSTALL_DIR}/images/favicon.ico"

