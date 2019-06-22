###
### Change ROUBAIX_PREPEND everytime you touch this script
###
ROUBAIX_PREPEND = "-17"

ROUBAIX_REVISION ?= "${DISTRO_PR}"
PKGR_${PN}-roubaix = "${PR}${ROUBAIX_PREPEND}${ROUBAIX_REVISION}"

RPROVIDES_${PN}-movial = "touchlink-ui-core"

do_install_append (){
	install -d ${D}/${INSTALL_DIR}/roubaix/
	# TL-3036 removed enphase and energy
	# TL-3143 added enphase back
    	${RSYNC_CMD} --exclude=font --exclude=energy --exclude=rules --exclude=libaudio ${S}/code/roubaix/ ${D}/${INSTALL_DIR}/roubaix/

        chmod +x ${D}/${INSTALL_DIR}/roubaix/utils/CameraStatusHelper.pyc
        chmod +x ${D}/${INSTALL_DIR}/roubaix/definitions.pyc
        chmod +x ${D}/${INSTALL_DIR}/roubaix/roubaix.pyc

        rm -rf ${D}/${INSTALL_DIR}/roubaix/utils/CameraStatusHelper.py
        rm -rf ${D}/${INSTALL_DIR}/roubaix/definitions.py
        rm -rf ${D}/${INSTALL_DIR}/roubaix/roubaix.py
}

pkg_postinst_ontarget_${PN}() {
#!/bin/sh -e
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
    ${INSTALL_DIR}/roubaix/conf_files/slim_line/roubaix_conf.json \
    ${INSTALL_DIR}/roubaix/conf_files/sky/roubaix_conf.json \
    ${INSTALL_DIR}/roubaix/conf_files/sly/roubaix_conf.json \
    ${INSTALL_DIR}/roubaix/conf_files/wall_sly/roubaix_conf.json \
    ${INSTALL_DIR}/roubaix/roubaix_demo_conf.json \
    ${INSTALL_DIR}/roubaix/roubaix_demo_log.json \
    ${INSTALL_DIR}/roubaix/roubaix_log.json \
    ${INSTALL_DIR}/roubaix/roubaix.qml \
    ${INSTALL_DIR}/roubaix/utils/* \
    ${INSTALL_DIR}/roubaix/helpers/* \
    ${INSTALL_DIR}/roubaix/definitions.pyc \
    ${INSTALL_DIR}/roubaix/roubaix.pyc \
    ${INSTALL_DIR}/roubaix/proxies/* \
    ${INSTALL_DIR}/roubaix/ErrorLogWatcherMixin.pyc \
    ${INSTALL_DIR}/roubaix/KeyEventDispatcher.pyc \
    ${INSTALL_DIR}/roubaix/FileMixin.pyc \
    ${INSTALL_DIR}/roubaix/ConnectionsMixin.pyc \
    ${INSTALL_DIR}/roubaix/AudioMixin.pyc \
    ${INSTALL_DIR}/roubaix/PlayAudioThread.pyc \
    ${INSTALL_DIR}/roubaix/FileMixin.py \
    ${INSTALL_DIR}/roubaix/ConnectionsMixin.py \
    ${INSTALL_DIR}/roubaix/AudioMixin.py \
    ${INSTALL_DIR}/roubaix/PlayAudioThread.py \
    ${INSTALL_DIR}/roubaix/migrations/* \
    ${sysconfdir}/init.d/roubaix \
    ${sysconfdir}/procman.d/roubaix \
    "

