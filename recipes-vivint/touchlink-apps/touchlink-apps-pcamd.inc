###
### Change PCAMD_PREPEND everything you touch this script
###
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
INHIBIT_PACKAGE_STRIP = "1"
PCAMD_PREPEND = "-1"

PCAMD_REVISION ?= "${DISTRO_PR}"
PKGR_${PN}-pcamd = "${PR}${PCAMD_PREPEND}${PCAMD_REVISION}"

PR = "r3"

do_compile_prepend() {
	# compile pcamd C code
	ln -sf ${STAGING_DIR}/${MACHINE}/usr/include/gnu/stubs-hard.h \
		${STAGING_DIR}/${MACHINE}/usr/include/gnu/stubs-soft.h
	make -C ${S}/code/pcamd/libpcam SYSTEM="SLIMLINE" \
		GST_DIR=${STAGING_DIR}/${MACHINE}/usr
}

do_install_append () {
	install -d ${D}/${INSTALL_DIR}/pcamd/
	${RSYNC_CMD} ${S}/code/pcamd/ ${D}/${INSTALL_DIR}/pcamd/
	chmod +x ${D}/${INSTALL_DIR}/pcamd/pcamd.pyo

	# pcamd library and binary
	install -d ${D}/${INSTALL_DIR}/lib
	cp -a ${S}/code/pcamd/libpcam/libpcam.so ${D}/${INSTALL_DIR}/lib

	# remove pcam libpcam dir
	rm -rf ${D}/${INSTALL_DIR}/pcamd/libpcam

	# install test script
	install -d ${D}/usr/local/bin
	cp -a ${S}/code/pcamd/scripts/pcamtest ${D}/usr/local/bin
	chmod 0755 ${D}/usr/local/bin/pcamtest
}

FILES_${PN}-pcamd = " \
	${INSTALL_DIR}/pcamd/*   \
	${INSTALL_DIR}/lib/libpcam.so	\
	${sysconfdir}/init.d/pcamd \
	${sysconfdir}/procman.d/pcamd \
	/usr/local/bin/pcamtest	\
	"

INSANE_SKIP_${PN}-pcamd = "installed-vs-shipped"
