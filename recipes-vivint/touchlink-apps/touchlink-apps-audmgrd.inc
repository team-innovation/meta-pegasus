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

do_install_append () {
	install -d ${D}/${INSTALL_DIR}/audmgrd/
        ${RSYNC_CMD} ${S}/code/audmgrd/ ${D}/${INSTALL_DIR}/audmgrd/
        chmod +x ${D}/${INSTALL_DIR}/audmgrd/audmgrd.pyo
}

FILES_${PN}-audmgrd = "\
       ${INSTALL_DIR}/audmgrd/*   \
	${sysconfdir}/init.d/audmgrd		\
	${sysconfdir}/procman.d/audmgrd	\
       "
