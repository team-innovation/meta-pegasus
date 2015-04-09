###
### Change UTILS_PREPEND everything you touch this script
###
UTILS_PREPEND = "-1"

UTILS_REVISION ?= "${DISTRO_PR}"
PKGR_${PN}-utils = "${PR}${UTILS_PREPEND}${UTILS_REVISION}"

do_install_append () {
	install -d ${D}/${INSTALL_DIR}/utils/
	${RSYNC_CMD} ${S}/code/utils/ ${D}/${INSTALL_DIR}/utils/
	chmod +x ${D}/${INSTALL_DIR}/utils/*
}

FILES_${PN}-utils = "\
       ${INSTALL_DIR}/utils/*"

             
