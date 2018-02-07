###
### Change VIDEOD_PREPEND everything you touch this script
###
VIDEOD_PREPEND = "-17"

VIDEOD_REVISION ?= "${DISTRO_PR}"
PKGR_${PN}-videod = "${PR}${VIDEOD_PREPEND}${VIDEOD_REVISION}"

do_install_append () {
	install -d ${D}/${INSTALL_DIR}/videod/
	${RSYNC_CMD} ${S}/code/videod/ ${D}/${INSTALL_DIR}/videod/
	chmod +x ${D}/${INSTALL_DIR}/videod/videod.pyc
}


FILES_${PN}-videod = "\
	${INSTALL_DIR}/videod/*   \
	${sysconfdir}/init.d/videod \
	${sysconfdir}/procman.d/videod \
       "

