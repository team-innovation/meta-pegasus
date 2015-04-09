###
### Change RHTML_PREPEND everytime you touch this script
###
RHTML_PREPEND = "-1"

RHTML_REVISION ?= "${DISTRO_PR}"
PKGR_${PN}-roubaix-html = "${PR}${RHTML_PREPEND}${RHTML_REVISION}"

FILES_${PN}-roubaix-html = "\
	${INSTALL_DIR}/roubaix/html/* \
    "

