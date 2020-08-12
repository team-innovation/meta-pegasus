###
### Change HUEI_PREPEND everything you touch this script
###
HUEI_PREPEND = "-2"

HUEI_REVISION ?= "${DISTRO_PR}"
PKGR_${PN}-huei = "${PR}${HUEI_PREPEND}${HUEI_REVISION}"

do_install_append () {
        install -d ${D}/${INSTALL_DIR}/huei/
        ${RSYNC_CMD} ${S}/code/huei/dist/ ${D}/${INSTALL_DIR}/huei/

}

FILES_${PN}-huei = "\
       ${INSTALL_DIR}/huei/* \
       "