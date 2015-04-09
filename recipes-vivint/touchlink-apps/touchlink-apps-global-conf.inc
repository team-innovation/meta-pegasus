###
### Change GLOBAL_CONF_PREPEND everytime you touch this script
###
GLOBAL_CONF_PREPEND = "-1"

GLOBAL_CONF_REVISION ?= "${DISTRO_PR}"
PKGR_${PN}-global-conf = "${PR}${GLOBAL_CONF_PREPEND}${GLOBAL_CONF_REVISION}"

do_install_append () {
	install -d ${D}/${INSTALL_DIR}/global_conf
	${RSYNC_CMD} ${S}/code/global_conf/ ${D}/${INSTALL_DIR}/global_conf
}

FILES_${PN}-global-conf = "\
	${INSTALL_DIR}/global_conf/*	\
	"


