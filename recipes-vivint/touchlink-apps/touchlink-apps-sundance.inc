###
### Change SUNDANCE_PREPEND everything you touch this script
###
SUNDANCE_PREPEND = "-3"


SUNDANCE_REVISION ?= "${DISTRO_PR}"

PKGR_${PN}-sundance = "${PR}${SUNDANCE_PREPEND}${SUNDANCE_REVISION}"
 

RDEPENDS_${PN}-sundance = "\
	python3-pyinotify \
	python3-requests \
	python3-multiprocessing \
	python3-mmap \
    python3-httpsig-na \
	"
do_install_append () {
	install -d ${D}/${INSTALL_DIR}/sundance/
        ${RSYNC_CMD} ${S}/code/sundance/ ${D}/${INSTALL_DIR}/sundance/
        chmod +x ${D}/${INSTALL_DIR}/sundance/sundance.pyo
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
	${INSTALL_DIR}/sundance/*	\
	${sysconfdir}/init.d/sundance		\
	${sysconfdir}/procman.d/sundance	\
	"

             
