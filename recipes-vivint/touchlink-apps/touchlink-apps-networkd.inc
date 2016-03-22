###
### Change NETWORKD_PREPEND everything you touch this script
###
NETWORKD_PREPEND = "-1"

NETWORKD_REVISION ?= "${DISTRO_PR}"
PKGR_${PN}-networkd = "${PR}${NETWORKD_PREPEND}${NETWORKD_REVISION}"

do_install_append () {
	install -d ${D}/${INSTALL_DIR}/networkd/
	${RSYNC_CMD} ${S}/code/networkd/ ${D}/${INSTALL_DIR}/networkd/
	chmod +x ${D}/${INSTALL_DIR}/networkd/networkd.pyo
}

FILES_${PN}-networkd = "\
	${INSTALL_DIR}/networkd/*	\
	${sysconfdir}/init.d/networkd		\
	${sysconfdir}/procman.d/networkd		\
	"


