###
### Change SUNDANCE_PREPEND everything you touch this script
###
SUNDANCE_PREPEND = "-4"


SUNDANCE_REVISION ?= "${DISTRO_PR}"

PKGR_${PN}-sundance = "${PR}${SUNDANCE_PREPEND}${SUNDANCE_REVISION}"

INITSCRIPT_PACKAGES += "${PN}-sundance"
INITSCRIPT_NAME_${PN}-sundance = "sundance"
INITSCRIPT_PARAMS_${PN}-sundance = "start 91 2 3 4 5 ."

RDEPENDS_${PN}-sundance = "\
	python3-pyinotify \
	python3-requests \
	python3-multiprocessing \
	python3-mmap \
	python3-soco \
    python3-jsonschema \
    python3-httpsig-na \
    zipgateway \
	"

inherit distutils3

export BUILD_SYS
export HOST_SYS
export STATIC_LIB_DIR

do_compile_prepend() {
    export ZWAVE_300_SERIES
    # Build plugin_server
    cd ${S}/code/sundance/plugins
    oe_runmake STATIC_LIB_DIR=${STAGING_DIR_TARGET}/usr/lib
    # Build plugin_server_legacy
    cd ${S}/code/sundance/plugins
    oe_runmake STATIC_LIB_DIR=${STAGING_DIR_TARGET}/usr/lib ZWAVE_300_SERIES=TRUE

    # Build extensions
    cd ${S}/code/sundance
    BUILD_SYS=${BUILD_SYS} HOST_SYS=${HOST_SYS} STATIC_LIB_DIR=${STAGING_DIR_TARGET}/usr/lib \
    python3 setup.py build ${DISTUTILS_BUILD_ARGS}
}

do_install_append () {
	install -d ${D}/${INSTALL_DIR}/sundance/
	install -d ${D}/${INSTALL_DIR}/sundance/proxies/
	install -d ${D}/${INSTALL_DIR}/sundance/connectors/
	install -d ${D}/${INSTALL_DIR}/sundance/definitions/
	install -d ${D}/${INSTALL_DIR}/sundance/helpers/
	install -d ${D}/${INSTALL_DIR}/sundance/migrations/
	install -d ${D}/${INSTALL_DIR}/sundance/translation/
	install -d ${D}/${INSTALL_DIR}/sundance/services/
	install -d ${D}/${INSTALL_DIR}/sundance/rulecore/
	install -d ${D}/${INSTALL_DIR}/sundance/vivint_generated/
    ${RSYNC_CMD} ${S}/code/sundance/proxies/ ${D}/${INSTALL_DIR}/sundance/proxies/
    ${RSYNC_CMD} ${S}/code/sundance/connectors/ ${D}/${INSTALL_DIR}/sundance/connectors/
    ${RSYNC_CMD} ${S}/code/sundance/definitions/ ${D}/${INSTALL_DIR}/sundance/definitions/
    ${RSYNC_CMD} ${S}/code/sundance/helpers/ ${D}/${INSTALL_DIR}/sundance/helpers/
    ${RSYNC_CMD} ${S}/code/sundance/migrations/ ${D}/${INSTALL_DIR}/sundance/migrations/
    ${RSYNC_CMD} ${S}/code/sundance/translation/ ${D}/${INSTALL_DIR}/sundance/translation/
    ${RSYNC_CMD} ${S}/code/sundance/services/ ${D}/${INSTALL_DIR}/sundance/services/
    ${RSYNC_CMD} ${S}/code/sundance/rulecore/ ${D}/${INSTALL_DIR}/sundance/rulecore/
    ${RSYNC_CMD} ${S}/code/sundance/vivint_generated/ ${D}/${INSTALL_DIR}/sundance/vivint_generated/
    cp -a ${S}/code/sundance/sundance.pyc ${D}/${INSTALL_DIR}/sundance/
    cp -a ${S}/code/sundance/sundance_conf.json ${D}/${INSTALL_DIR}/sundance/
    cp -a ${S}/code/sundance/sundance_log.json ${D}/${INSTALL_DIR}/sundance/
    chmod +x ${D}/${INSTALL_DIR}/sundance/sundance.pyc

    install -d ${D}/${libdir}/${PYTHON_DIR}/site-packages
    cp -a ${S}/code/sundance/build/lib.linux-x86_64-3.5/iotivity_current.cpython-35m-arm-linux-gnueabihf.so ${D}/${libdir}/${PYTHON_DIR}/site-packages
    cp -a ${S}/code/sundance/build/lib.linux-x86_64-3.5/iotivity_legacy.cpython-35m-arm-linux-gnueabihf.so ${D}/${libdir}/${PYTHON_DIR}/site-packages

    install -d ${D}/${prefix}/local/etc
    cp -a ${S}/code/sundance/zwave_cmd_class.cfg ${D}/${prefix}/local/etc

    install -d ${D}/${prefix}/local/etc
    cp -a ${S}/code/sundance/zwave_device_rec.txt ${D}/${prefix}/local/etc

    install -d ${D}/${libdir}
    cp -a ${S}/code/sundance/plugins/libplugin_server_legacy.so ${D}/${libdir}
    
    install -d ${D}/${libdir}
    cp -a ${S}/code/sundance/plugins/libplugin_server_current.so ${D}/${libdir}
    
    install -d ${D}/${libdir}
    cp -a ${S}/code/sundance/plugins/hue/libhueplugin.so ${D}/${libdir}
    
    install -d ${D}/${libdir}
    cp -a ${S}/code/sundance/plugins/nest/libnestplugin.so ${D}/${libdir}
}

pkg_postinst_${PN}-sundance () {
#!/bin/sh -e
# Post install to make sure we have the correct setup for sundance
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
else
    exit 1
fi
}

FILES_${PN}-sundance-proxies = "\
	${INSTALL_DIR}/sundance/proxies/*	\
	"

FILES_${PN}-sundance = "\
	${INSTALL_DIR}/sundance/connectors/*	\
	${INSTALL_DIR}/sundance/definitions/*	\
	${INSTALL_DIR}/sundance/helpers/*	\
	${INSTALL_DIR}/sundance/migrations/*	\
	${INSTALL_DIR}/sundance/translation/*	\
	${INSTALL_DIR}/sundance/services/*	\
	${INSTALL_DIR}/sundance/rulecore/*	\
	${INSTALL_DIR}/sundance/vivint_generated/*	\
	${INSTALL_DIR}/sundance/sundance*	\
	${sysconfdir}/init.d/sundance		\
	${sysconfdir}/init.d/mpm_client		\
	${sysconfdir}/procman.d/sundance	\
    ${prefix}/local/etc/zwave_cmd_class.cfg \
    ${prefix}/local/etc/zwave_device_rec.txt \
    ${libdir}/libplugin_server_legacy.so \
    ${libdir}/libplugin_server_current.so \
    ${libdir}/libhueplugin.so \
    ${libdir}/libnestplugin.so \
	"

             
