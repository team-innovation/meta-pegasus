###
### Change ADCD_PREPEND everything you touch this script
###
ADCD_PREPEND = "-5"

ADCD_REVISION ?= "${DISTRO_PR}"
PKGR_${PN}-adcd = "${PR}${ADCD_PREPEND}${ADCD_REVISION}"

RDEPENDS_${PN}-adcd = "\
	python3-ctypes \
	"

do_install_append(){
	install -d ${D}/${INSTALL_DIR}/adcd/
        ${RSYNC_CMD} ${S}/code/adcd/ ${D}/${INSTALL_DIR}/adcd/
        chmod +x ${D}/${INSTALL_DIR}/adcd/adcd.pyc

	# fix up libadc
	rm -f ${D}/${INSTALL_DIR}/adcd/libadc/libskytelLibrary.so
	mv  ${D}/${INSTALL_DIR}/adcd/libadc/libskytelLibrary-ARM.so ${D}/${INSTALL_DIR}/adcd/libadc/libskytelLibrary.so
	# strip the .so file
	${STRIP} --strip-unneeded ${D}/${INSTALL_DIR}/adcd/libadc/libskytelLibrary.so

	# remove non .so files in libadc
	rm -f ${D}/${INSTALL_DIR}/adcd/libadc/*.h
	rm -f ${D}/${INSTALL_DIR}/adcd/libadc/*.c
	rm -f ${D}/${INSTALL_DIR}/adcd/libadc/Makefile
}


pkg_postrm_${PN}-adcd () {
#!/bin/sh -e
# Post remove to make sure we have the correct setup 
#
if [ x"$D" = "x" ]; then
     logging()
     {
        if busybox ps | grep psplash | grep -qv grep
        then
            psplash-write "MSG $*"
        fi
        echo $*
        if [ ! -d /media/extra/update ]
        then
        mkdir -p /media/extra/update
        fi
        echo $(date) $* >> /media/extra/update/.firmware_update_status
        logger $*
     }

    if [ -d /opt/2gig/adcd ]
    then
            logging "Remove adcd directory..."
            rm -rf /opt/2gig/adcd
    fi
else
    exit 1
fi
}

FILES_${PN}-adcd = "\
       ${INSTALL_DIR}/adcd/*   \
	${sysconfdir}/init.d/adcd		\
	${sysconfdir}/procman.d/adcd		\
       "

INSANE_SKIP_${PN}-adcd = "True"
