###
### Change LAUNCHERD_PREPEND everything you touch this script
###
LAUNCHERD_PREPEND = "-4"

LAUNCHERD_REVISION ?= "${DISTRO_PR}"
PKGR_${PN}-launcherd = "${PR}${LAUNCHERD_PREPEND}${LAUNCHERD_REVISION}"

do_install_append() {
	install -d ${D}/${INSTALL_DIR}/launcherd/
        ${RSYNC_CMD} ${S}/code/launcherd/ ${D}/${INSTALL_DIR}/launcherd/
        chmod +x ${D}/${INSTALL_DIR}/launcherd/python-launcher-daemon.pyc
        chmod +x ${D}/${INSTALL_DIR}/launcherd/python-launcher.pyc
}

FILES_${PN}-launcherd = "\
       ${INSTALL_DIR}/launcherd/*   \
       ${sysconfdir}/init.d/launcherd \
       "

             
