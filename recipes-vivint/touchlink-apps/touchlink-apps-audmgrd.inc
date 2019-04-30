###
### Change AUDMGRD_PREPEND everything you touch this script
###
AUDMGRD_PREPEND = "-1"

AUDIO_REVISION ?= "${DISTRO_PR}"
PKGR_${PN}-audmgrd = "${PR}${AUDMGRD_PREPEND}${AUDIO_REVISION}"

INITSCRIPT_PACKAGES += "${PN}-audmgrd"
INITSCRIPT_NAME_${PN}-audmgrd = "audmgrd"
INITSCRIPT_PARAMS_${PN}-audmgrd = "start 28 2 3 4 5 ."

RDEPENDS_${PN}-audmgrd = "\
	python3-audio \
	python3-pyalsaaudio \
    gstreamer1.0 \
"
do_compile_prepend() {
        # compile dbc audio C code
        make -C ${S}/code/audmgrd/libaudio SYSTEM="SLIMLINE"
}

do_install_append () {
	install -d ${D}/${INSTALL_DIR}/audmgrd/
        ${RSYNC_CMD} ${S}/code/audmgrd/ ${D}/${INSTALL_DIR}/audmgrd/
        chmod +x ${D}/${INSTALL_DIR}/audmgrd/audmgrd.pyc
        install -d ${D}/${INSTALL_DIR}/lib
        install ${S}/code/audmgrd/libaudio/libaudio.so ${D}/${INSTALL_DIR}/lib
}

FILES_${PN}-audmgrd = "\
       ${INSTALL_DIR}/audmgrd/*   \
       ${INSTALL_DIR}/lib/libaudio.so      \
	${sysconfdir}/init.d/audmgrd		\
	${sysconfdir}/procman.d/audmgrd	\
       "
