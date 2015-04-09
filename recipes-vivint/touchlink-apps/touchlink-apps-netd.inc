###
### Change NETD_PREPEND everything you touch this script
###
NETD_PREPEND = "-1"

NETD_REVISION ?= "${DISTRO_PR}"
PKGR_${PN}-netd = "${PR}${NETD_PREPEND}${NETD_REVISION}"

do_install_append () {
	install -d ${D}/${INSTALL_DIR}/netd/
        ${RSYNC_CMD} ${S}/code/netd/ ${D}/${INSTALL_DIR}/netd/
        chmod +x ${D}/${INSTALL_DIR}/netd/netd.pyo

	install -d ${D}/usr/local/bin
	cp -a ${S}/code/netd/scripts/speedtest ${D}/usr/local/bin
	chmod 0755 ${D}/usr/local/bin/*

}

FILES_${PN}-netd = "\
       ${INSTALL_DIR}/netd/*   \
	${sysconfdir}/init.d/netd		\
	${sysconfdir}/procman.d/netd		\
       /usr/local/bin/speedtest	\
       "

             
