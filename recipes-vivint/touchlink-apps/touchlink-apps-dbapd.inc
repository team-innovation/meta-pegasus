###
### Change DBAPD_PREPEND everything you touch this script
###
DBAPD_PREPEND = "-1"

DBAPD_REVISION ?= "${DISTRO_PR}"
PKGR_${PN}-dbapd = "${PR}${DBAPD_PREPEND}${DBAPD_REVISION}"

do_install_append () {
	install -d ${D}/${INSTALL_DIR}/dbapd/
	${RSYNC_CMD} ${S}/code/dbapd/ ${D}/${INSTALL_DIR}/dbapd/
	chmod +x ${D}/${INSTALL_DIR}/dbapd/dbapd.pyc
}

RDEPENDS_${PN}-dbapd = "openssl openssl-misc"

FILES_${PN}-dbapd = "\
	${INSTALL_DIR}/dbapd/*	\
	${sysconfdir}/init.d/dbapd		\
	${sysconfdir}/procman.d/dbapd		\
	"


