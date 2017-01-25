###
### Change FRAMEWORK_PREPEND everything you touch this script
###
FRAMEWORK_PREPEND = "-3"

FRAMEWORK_REVISION ?= "${DISTRO_PR}"
PKGR_${PN}-framework = "${PR}${FRAMEWORK_PREPEND}${FRAMEWORK_REVISION}"

RDEPENDS_${PN}-framework = "\
        python3-dateutil \
        python3-sparsedict \
	"

do_install_append () {

    install -d ${D}/${FRAMEWORK_DIR}/taurine/
	${RSYNC_CMD} ${S}/code/framework/taurine/ ${D}/${FRAMEWORK_DIR}/taurine/

	find ${D}/${FRAMEWORK_DIR}/taurine -name proxies -prune -o -name *.py -print | xargs rm -f
}

FILES_${PN}-framework = "\
	${FRAMEWORK_DIR}/* \
	"	
