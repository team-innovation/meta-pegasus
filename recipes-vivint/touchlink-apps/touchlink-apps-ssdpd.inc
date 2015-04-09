###
### Change SSDPD_PREPEND everything you touch this script
###
SSDPD_PREPEND = "-1"

SSDPD_REVISION ?= "${DISTRO_PR}"
PKGR_${PN}-ssdpd = "${PR}${SSDPD_PREPEND}${SSDPD_REVISION}"

do_install_append () {
	install -d ${D}/${INSTALL_DIR}/ssdpd/
	${RSYNC_CMD} ${S}/code/ssdpd/ ${D}/${INSTALL_DIR}/ssdpd/
	chmod +x ${D}/${INSTALL_DIR}/ssdpd/ssdpd.pyo
}

FILES_${PN}-ssdpd = "\
	${INSTALL_DIR}/ssdpd/*	\
	${sysconfdir}/init.d/ssdpd		\
	${sysconfdir}/procman.d/ssdpd		\
	"


