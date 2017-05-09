###
### Change AUDMGRD_PREPEND everything you touch this script
###
AUDMGRD_PREPEND = "-1"

AUDIO_REVISION ?= "${DISTRO_PR}"
PKGR_${PN}-audmgrd = "${PR}${AUDMGRD_PREPEND}${AUDIO_REVISION}"

RDEPENDS_${PN}-audmgrd = "\
	python3-audio \
	python3-pyalsaaudio \
"
do_compile_prepend() {
        # compile dbc audio C code
        make -C ${S}/code/audmgrd/libaudio       \
              GST_DIR=${STAGING_DIR}/${HOST_ARCH}v7a${HOST_VENDOR}-${HOST_OS}/usr \
              PKG_CONFIG_ALLOW_SYSTEM_LIBS=1
}
do_install_append () {
	install -d ${D}/${INSTALL_DIR}/audmgrd/
        ${RSYNC_CMD} ${S}/code/audmgrd/ ${D}/${INSTALL_DIR}/audmgrd/
        chmod +x ${D}/${INSTALL_DIR}/audmgrd/audmgrd.pyo
        install -d ${D}/${INSTALL_DIR}/lib
        cp -a ${S}/code/audmgrd/libaudio/libaudio.so ${D}/${INSTALL_DIR}/lib
}

FILES_${PN}-audmgrd = "\
       ${INSTALL_DIR}/audmgrd/*   \
       ${INSTALL_DIR}/lib/libaudio.so      \
	${sysconfdir}/init.d/audmgrd		\
	${sysconfdir}/procman.d/audmgrd	\
       "
