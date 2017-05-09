###
### Change ROUBAIX_SERVICES_PREPEND everytime you touch this script
###
ROUBAIX_SERVICES_PREPEND = "-3"

ROUBAIX_SERVICES_REVISION ?= "${DISTRO_PR}"
PKGR_${PN}-roubaix-services = "${PR}${ROUBAIX_SERVICES_PREPEND}${ROUBAIX_SERVICES_REVISION}"

FILES_${PN}-roubaix-services = "\
	${INSTALL_DIR}/roubaix/services/* \
    ${INSTALL_DIR}/roubaix/proxies/* \
	"

do_install_append (){ 
   install -d ${D}/${INSTALL_DIR}/roubaix/services/
	# TL-3036 removed enphase and energy
	# TL-3143 added enphase back
    ${RSYNC_CMD} --exclude=font --exclude=energy --exclude=rules ${S}/code/roubaix/services/ ${D}/${INSTALL_DIR}/roubaix/services/
    ${RSYNC_CMD} --exclude=font --exclude=energy --exclude=rules ${S}/code/roubaix/proxies/ ${D}/${INSTALL_DIR}/roubaix/proxies/
}

