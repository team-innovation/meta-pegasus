###
### Change PCAMD_PREPEND everything you touch this script
###
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
INHIBIT_PACKAGE_STRIP = "1"
PCAMD_PREPEND = "-1"

PCAMD_REVISION ?= "${DISTRO_PR}"
PKGR_${PN}-pcamd = "${PR}${PCAMD_PREPEND}${PCAMD_REVISION}"

PR = "r7"

do_compile_prepend() {
	# compile pcamd C code
	ln -sf ${STAGING_DIR}/${MACHINE}/usr/include/gnu/stubs-hard.h \
		${STAGING_DIR}/${MACHINE}/usr/include/gnu/stubs-soft.h
	make -C ${S}/code/pcamd/libpcam SYSTEM="SLIMLINE" 
}

do_install_append () {
	install -d ${D}/${INSTALL_DIR}/pcamd/
	${RSYNC_CMD} ${S}/code/pcamd/ ${D}/${INSTALL_DIR}/pcamd/
	chmod +x ${D}/${INSTALL_DIR}/pcamd/pcamd.pyc

	# remove pcam libpcam dir
	rm -rf ${D}/${INSTALL_DIR}/pcamd/libpcam
}

FILES_${PN}-pcamd = " \
	${INSTALL_DIR}/pcamd/*   \
	${sysconfdir}/init.d/pcamd \
	${sysconfdir}/procman.d/pcamd \
	"

INSANE_SKIP_${PN}-pcamd = "installed-vs-shipped"
