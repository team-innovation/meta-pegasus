###
### Change HTTPD_PREPEND everything you touch this script
###
HTTPD_PREPEND = "-2"

HTTPD_REVISION ?= "${DISTRO_PR}"
PKGR_${PN}-httpd = "${PR}${HTTPD_PREPEND}${HTTPD_REVISION}"

do_install_append () {
	install -d ${D}/${INSTALL_DIR}/httpd/
        ${RSYNC_CMD} ${S}/code/httpd/ ${D}/${INSTALL_DIR}/httpd/
        chmod +x ${D}/${INSTALL_DIR}/httpd/httpd.pyc
}

FILES_${PN}-httpd = "\
       ${INSTALL_DIR}/httpd/*   \
	${sysconfdir}/init.d/httpd		\
       "

