SYSTEMD_SERVICE_${PN}-sundance = "sundance.service"
SYSTEMD_AUTO_ENABLE_${PN}-sundance = "enable"

RDEPENDS_${PN}-sundance = "\
    dect-python-cmnd \
	python3-pyinotify \
	python3-requests \
	python3-multiprocessing \
	python3-mmap \
	python3-soco \
    python3-jsonschema \
    python3-httpsig-na \
    python3-paho-mqtt \
    python3-phue \
    python3-requests \
    python3-requests-toolbelt \
    python3-soco \
    python3-brisa \
    python3-certifi \
    python3-transitions \
    python3-sentry-sdk \
    python3-aioconsole \
    zipgateway \
    paho-mqtt-cpp \
    bash \
    python3-grpcio \
    python3-grpcio-tools \
    python3-bson \
    python3-semver \
	python3-google-auth \
    pluginserver \
"

inherit distutils3

export BUILD_SYS
export HOST_SYS
export STATIC_LIB_DIR
export INC_DIR


do_compile_prepend() {
    export NO_MAPCS_FRAME

    # aarch64-poky-linux-g++ does not support -mapcs-frame so don't use it
    if [ ${HOST_SYS} != "aarch64-poky-linux" ]; then
        use_mapcs_frame=""
    else
        use_mapcs_frame="NO_MAPCS_FRAME=TRUE"
    fi

    # Build extensions
    cd ${S}/code/sundance
    BUILD_SYS=${BUILD_SYS} HOST_SYS=${HOST_SYS} STATIC_LIB_DIR=${STAGING_DIR_TARGET}/usr/lib INC_DIR=${STAGING_DIR_TARGET}/usr/include \
    python3 setup.py build ${DISTUTILS_BUILD_ARGS}
}

do_install_append () {
	install -d ${D}/${INSTALL_DIR}/sundance/
	install -d ${D}/${INSTALL_DIR}/sundance/proxies/
	install -d ${D}/${INSTALL_DIR}/sundance/connectors/
	install -d ${D}/${INSTALL_DIR}/sundance/conf_files/
	install -d ${D}/${INSTALL_DIR}/sundance/definitions/
	install -d ${D}/${INSTALL_DIR}/sundance/helpers/
	install -d ${D}/${INSTALL_DIR}/sundance/migrations/
	install -d ${D}/${INSTALL_DIR}/sundance/translation/
	install -d ${D}/${INSTALL_DIR}/sundance/services/
	install -d ${D}/${INSTALL_DIR}/sundance/rulecore/
	install -d ${D}/${INSTALL_DIR}/sundance/vivint_generated/
    ${RSYNC_CMD} ${S}/code/sundance/proxies/ ${D}/${INSTALL_DIR}/sundance/proxies/
    ${RSYNC_CMD} ${S}/code/sundance/connectors/ ${D}/${INSTALL_DIR}/sundance/connectors/
    ${RSYNC_CMD} ${S}/code/sundance/conf_files/ ${D}/${INSTALL_DIR}/sundance/conf_files/
    ${RSYNC_CMD} ${S}/code/sundance/definitions/ ${D}/${INSTALL_DIR}/sundance/definitions/
    ${RSYNC_CMD} ${S}/code/sundance/helpers/ ${D}/${INSTALL_DIR}/sundance/helpers/
    ${RSYNC_CMD} ${S}/code/sundance/migrations/ ${D}/${INSTALL_DIR}/sundance/migrations/
    ${RSYNC_CMD} ${S}/code/sundance/translation/ ${D}/${INSTALL_DIR}/sundance/translation/
    ${RSYNC_CMD} --exclude=camera-connect --exclude=*.pb.cc --exclude=*.pb.h ${S}/code/sundance/services/ ${D}/${INSTALL_DIR}/sundance/services/
    ${RSYNC_CMD} ${S}/code/sundance/rulecore/ ${D}/${INSTALL_DIR}/sundance/rulecore/
    ${RSYNC_CMD} ${S}/code/sundance/vivint_generated/ ${D}/${INSTALL_DIR}/sundance/vivint_generated/
    install -m 644 ${S}/code/sundance/sundance.pyc ${D}/${INSTALL_DIR}/sundance/
    install -m 644 ${S}/code/sundance/sundance_conf.json ${D}/${INSTALL_DIR}/sundance/
    install -m 644 ${S}/code/sundance/sundance_log.json ${D}/${INSTALL_DIR}/sundance/
    chmod +x ${D}/${INSTALL_DIR}/sundance/sundance.pyc

    install -d ${D}/${libdir}/${PYTHON_DIR}/site-packages

    cp ${S}/code/sundance/build/lib.linux-x86_64-*/iotivity_*.cpython*.so ${D}/${libdir}/${PYTHON_DIR}/site-packages
    chmod 644 ${D}/${libdir}/${PYTHON_DIR}/site-packages/iotivity*.cpython-*.so

    install -d ${D}/${prefix}/local/etc
    install -m 644 ${S}/code/sundance/zwave_cmd_class.cfg ${D}/${prefix}/local/etc
    install -m 644 ${S}/code/sundance/zwave_device_rec.txt ${D}/${prefix}/local/etc

    # systemd services
    install -d ${D}${systemd_unitdir}/system
    install -m 0644 ${WORKDIR}/systemd/sundance.service ${D}${systemd_unitdir}/system
}

pkg_postinst_ontarget_${PN}-sundance () {
    #!/bin/sh -e
    # Post install to make sure we have the correct setup for sundance

    logging() {
        if busybox ps | grep psplash | grep -qv grep
        then
            psplash-write "MSG $*" || true
        fi
        echo $*
        if [ ! -d /media/extra/update ]
        then
        mkdir -p /media/extra/update
        fi
        echo $(date) $* >> /media/extra/update/.firmware_update_status
        logger $*
    }
     
    if [ ! -d /media/extra/db ]
    then	
        logging "Creating db directory..."
        mkdir /media/extra/db
        if [ -e /media/extra/sundance.db ]
        then
            logging "Moving sundance.db to db directory..."
            mv /media/extra/sundance.db /media/extra/db/
            ln -s /media/extra/db/sundance.db /media/extra/sundance.db
        fi
    fi

    if [ ! -d /media/extra/log/error ]
    then
        logging "Creating log directory..."
        mkdir -p /media/extra/log/error
    fi

    if [ -d /media/extra/log/error ]
    then
        logging "Removing all error log in /media/extra/log..."
        rm -f /media/extra/log/*_error.log*
    fi
}

FILES_${PN}-sundance-proxies = "\
	${INSTALL_DIR}/sundance/proxies/* \
	${INSTALL_DIR}/sundance/services/devices/generated/grpc/* \
"

FILES_${PN}-sundance = "\
	${INSTALL_DIR}/sundance/connectors/* \
	${INSTALL_DIR}/sundance/conf_files/* \
	${INSTALL_DIR}/sundance/definitions/* \
	${INSTALL_DIR}/sundance/helpers/* \
	${INSTALL_DIR}/sundance/migrations/* \
	${INSTALL_DIR}/sundance/translation/* \
	${INSTALL_DIR}/sundance/services/* \
	${INSTALL_DIR}/sundance/rulecore/* \
	${INSTALL_DIR}/sundance/vivint_generated/* \
	${INSTALL_DIR}/sundance/sundance* \
	${sysconfdir}/init.d/sundance \
	${sysconfdir}/procman.d/sundance \
    ${prefix}/local/etc/zwave_cmd_class.cfg \
    ${prefix}/local/etc/zwave_device_rec.txt \
	${systemd_unitdir}/system/sundance.service \
"
