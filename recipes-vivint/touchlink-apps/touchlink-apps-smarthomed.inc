###
### Change SMARTHOMED_PREPEND everytime you touch this script
###
SMARTHOMED_PREPEND = "-1"

SMARTHOMED_REVISION ?= "${DISTRO_PR}"
PKGR_${PN}-smarthomed = "${PR}${SMARTHOMED_PREPEND}${SMARTHOMED_REVISION}"

do_install_append () {
	install -d ${D}/${INSTALL_DIR}/smarthomed/
	${RSYNC_CMD} ${S}/code/smarthomed/ ${D}/${INSTALL_DIR}/smarthomed/
	chmod +x ${D}/${INSTALL_DIR}/smarthomed/smarthomed.pyo
}

pkg_postinst_${PN}-smarthomed () {
#!/bin/sh -e
# Post install to make sure we have the correct setup for smarthomed
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
        if [ -e /media/extra/smarthomed.db ]
        then
            logging "Moving smarthomed.db to db directory..."
            mv /media/extra/smarthomed.db /media/extra/db/
            ln -s /media/extra/db/smarthomed.db /media/extra/smarthomed.db
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

FILES_${PN}-smarthomed-proxies = "\
	${INSTALL_DIR}/smarthomed/proxies/*	\
	"
FILES_${PN}-smarthomed = "\
	${INSTALL_DIR}/smarthomed/*	\
	${sysconfdir}/init.d/smarthomed		\
	${sysconfdir}/procman.d/smarthomed		\
	"


