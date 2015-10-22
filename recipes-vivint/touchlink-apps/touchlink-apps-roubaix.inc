###
### Change ROUBAIX_PREPEND everytime you touch this script
###
ROUBAIX_PREPEND = "-12"

ROUBAIX_REVISION ?= "${DISTRO_PR}"
PKGR_${PN}-roubaix = "${PR}${ROUBAIX_PREPEND}${ROUBAIX_REVISION}"

INITSCRIPT_PACKAGES += "${PN}-roubaix"
INITSCRIPT_NAME_${PN}-roubaix = "roubaix"
INITSCRIPT_PARAMS_${PN}-roubaix = "stop 19 runlvl 0 6 ."

RPROVIDES_${PN}-movial = "touchlink-ui-core"

do_install_append (){
	install -d ${D}/${INSTALL_DIR}/roubaix/
	# TL-3036 removed enphase and energy
	# TL-3143 added enphase back
    	${RSYNC_CMD} --exclude=font --exclude=energy --exclude=rules ${S}/code/roubaix/ ${D}/${INSTALL_DIR}/roubaix/

        chmod +x ${D}/${INSTALL_DIR}/roubaix/CameraStatusHelper.pyo
        chmod +x ${D}/${INSTALL_DIR}/roubaix/definitions.pyo
        chmod +x ${D}/${INSTALL_DIR}/roubaix/roubaix.pyo

        rm -rf ${D}/${INSTALL_DIR}/roubaix/CameraStatusHelper.py
        rm -rf ${D}/${INSTALL_DIR}/roubaix/definitions.py
        rm -rf ${D}/${INSTALL_DIR}/roubaix/roubaix.py

	# move roubaix_slimline_conf.json to roubaix_slimline.json 
	rm -f ${D}/${INSTALL_DIR}/roubaix/roubaix_conf.json
	mv ${D}/${INSTALL_DIR}/roubaix/roubaix_slimline_conf.json ${D}/${INSTALL_DIR}/roubaix/roubaix_conf.json
}

pkg_postinst_${PN}-roubaix () {
#!/bin/sh
# Post install to make sure we have the correct setup for roubaix 
#
 logging()
 {
    if busybox ps | grep psplash | grep -qv grep
    then
        psplash-write "MSG $*"
    fi
    echo $*
    logger $*
 }
 
if [ ! -d /media/extra/conf ]
then
	logging "Creating conf directory..."
	mkdir -p /media/extra/conf
fi

if [ -f /media/extra/pointercal ]
then
        logging "Moving /media/extra/pointercal file to /media/extra/conf..."
        mv /media/extra/pointercal /media/extra/conf
fi
}

FILES_${PN}-roubaix = "\
    ${INSTALL_DIR}/roubaix/roubaix_conf.json   \
    ${INSTALL_DIR}/roubaix/roubaix_demo_conf.json   \
    ${INSTALL_DIR}/roubaix/roubaix_log.json		\
    ${INSTALL_DIR}/roubaix/roubaix.qml	\
    ${INSTALL_DIR}/roubaix/CameraStatusHelper.pyo \
    ${INSTALL_DIR}/roubaix/definitions.pyo	\
    ${INSTALL_DIR}/roubaix/roubaix.pyo	\
    ${INSTALL_DIR}/roubaix/proxies/*	\
    ${INSTALL_DIR}/roubaix/FileMixin.pyo \
    ${INSTALL_DIR}/roubaix/ConnectionsMixin.pyo \
    ${INSTALL_DIR}/roubaix/AudioMixin.pyo \
    ${INSTALL_DIR}/roubaix/PlayAudioThread.pyo \
    ${INSTALL_DIR}/roubaix/migrations/* \
    ${sysconfdir}/init.d/roubaix		\
    ${sysconfdir}/procman.d/roubaix	\
    "

