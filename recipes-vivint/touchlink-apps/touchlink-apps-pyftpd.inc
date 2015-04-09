###
### Change RTSPD_PREPEND everything you touch this script
###
PYFTPD_PREPEND = "-1"

PYFTPD_REVISION ?= "${DISTRO_PR}"
PKGR_${PN}-pyftpd = "${PR}${PYFTPD_PREPEND}${PYFTPD_REVISION}"
 
RDEPENDS_${PN}-pyftpd = "\
	python3-pyftpdlib \
"

do_install_append () {
	install -d ${D}/${INSTALL_DIR}/pyftpd/
	${RSYNC_CMD} ${S}/code/pyftpd/ ${D}/${INSTALL_DIR}/pyftpd/
	chmod +x ${D}/${INSTALL_DIR}/pyftpd/pyftpd.pyo
}


FILES_${PN}-pyftpd = "\
       ${INSTALL_DIR}/pyftpd/*   \
	${sysconfdir}/init.d/pyftpd \
	${sysconfdir}/procman.d/pyftpd \
   "
