###
### Change RTSPD_PREPEND everything you touch this script
###
RTSPD_PREPEND = "-12"

RTSPD_REVISION ?= "${DISTRO_PR}"
PKGR_${PN}-rtspd = "${PR}${RTSPD_PREPEND}${RTSPD_REVISION}"
 
RDEPENDS_${PN}-rtspd = "\
	python3-live555proxy \
"

do_install_append () {
	install -d ${D}/${INSTALL_DIR}/rtspd/
	${RSYNC_CMD} ${S}/code/rtspd/ ${D}/${INSTALL_DIR}/rtspd/
	chmod +x ${D}/${INSTALL_DIR}/rtspd/rtspd.pyo
}


FILES_${PN}-rtspd = "\
       ${INSTALL_DIR}/rtspd/*   \
	${sysconfdir}/init.d/rtspd \
	${sysconfdir}/procman.d/rtspd \
       "
             
INSANE_SKIP_${PN}-rtspd = "True"
