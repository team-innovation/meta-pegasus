###
### Change USAGE_ANALYTICS_PREPEND everything you touch this script
###
USAGE_ANALYTICS_PREPEND = "-1"

USAGE_ANALYTICS_REVISION ?= "${DISTRO_PR}"
PKGR_${PN}-usage-analytics = "${PR}${USAGE_ANALYTICS_PREPEND}${USAGE_ANALYTICS_REVISION}"

do_install_append () {
	install -d ${D}/${INSTALL_DIR}/usage_analytics/
	${RSYNC_CMD} ${S}/code/usage_analytics/ ${D}/${INSTALL_DIR}/usage_analytics/
	chmod +x ${D}/${INSTALL_DIR}/usage_analytics/usage_analytics.pyo
}

FILES_${PN}-usage-analytics = "\
	${INSTALL_DIR}/usage_analytics/*	\
	${sysconfdir}/init.d/usage_analytics		\
	${sysconfdir}/procman.d/usage_analytics		\
	"


