###
### Change ADTD_PREPEND everything you touch this script
###
ADTD_PREPEND = "-1"

ADTD_REVISION ?= "${DISTRO_PR}"
PKGR_${PN}-adtd = "${PR}${ADTD_PREPEND}${ADTD_REVISION}"

RDEPENDS_${PN}-adtd = "\
touchlink-adt-issserver-lib \
touchlink-adt-issserver-bootspin \
touchlink-adt-issserver-firmware \
"

do_install_append () {
	install -d ${D}/${INSTALL_DIR}/adtd/
        ${RSYNC_CMD} ${S}/code/adtd/ ${D}/${INSTALL_DIR}/adtd/
        chmod +x ${D}/${INSTALL_DIR}/adtd/adtd.pyc
}

FILES_${PN}-adtd = "\
       ${INSTALL_DIR}/adtd/*   \
	${sysconfdir}/init.d/adtd		\
	${sysconfdir}/procman.d/adtd		\
       "

