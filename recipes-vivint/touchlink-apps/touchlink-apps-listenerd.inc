###
### Change LISTENERD_PREPEND everytime you touch this script
###
LISTENERD_PREPEND = "-1"

LISTENERD_REVISION ?= "${DISTRO_PR}"
PKGR_${PN}-listenerd = "${PR}${LISTENERD_PREPEND}${LISTENERD_REVISION}"

do_install_append () {
	install -d ${D}/${INSTALL_DIR}/listenerd/
	${RSYNC_CMD} ${S}/code/listenerd/ ${D}/${INSTALL_DIR}/listenerd/
	chmod +x ${D}/${INSTALL_DIR}/listenerd/listenerd.pyc
}

FILES_${PN}-listenerd-proxies = "\
	${INSTALL_DIR}/listenerd/proxies/*	\
	"
FILES_${PN}-listenerd = "\
	${INSTALL_DIR}/listenerd/*	\
	${sysconfdir}/init.d/listenerd		\
	${sysconfdir}/procman.d/listenerd		\
	"


