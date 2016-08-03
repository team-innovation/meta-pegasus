###
### Change ROUBAIX_SERVICES_PREPEND everytime you touch this script
###
ROUBAIX_SERVICES_PREPEND = "-3"

ROUBAIX_SERVICES_REVISION ?= "${DISTRO_PR}"
PKGR_${PN}-roubaix-services = "${PR}${ROUBAIX_SERVICES_PREPEND}${ROUBAIX_SERVICES_REVISION}"

FILES_${PN}-roubaix-services = "\
	${INSTALL_DIR}/roubaix/services/* \
    ${INSTALL_DIR}/lib/libaudio.so \
	"

do_compile_prepend() {
        # compile dbc audio C code
        make -C ${S}/code/roubaix/libaudio SYSTEM="SLIMLINE"  
}

do_install_append (){ 
   install -d ${D}/${INSTALL_DIR}/lib
   cp -a ${S}/code/roubaix/libaudio/libaudio.so ${D}/${INSTALL_DIR}/lib
}

