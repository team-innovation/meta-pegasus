###
### Change WEBD_PREPEND everything you touch this script
###
WEBD_PREPEND = "-2"

WEBD_REVISION ?= "${DISTRO_PR}"
PKGR_${PN}-webd = "${PR}${WEBD_PREPEND}${WEBD_REVISION}"

RDEPENDS_${PN}-webd = "\
	python3-cherrypy \
	"
do_install_append () {
        install -d ${D}/${INSTALL_DIR}/webd/
        ${RSYNC_CMD} ${S}/code/webd/ ${D}/${INSTALL_DIR}/webd/
        chmod +x ${D}/${INSTALL_DIR}/webd/webd.pyc
}

FILES_${PN}-webd = "\
       ${INSTALL_DIR}/webd/*   \
	${sysconfdir}/init.d/webd		\
       "
