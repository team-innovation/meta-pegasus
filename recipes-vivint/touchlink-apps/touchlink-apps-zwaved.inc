###
### Change ZWAVED_PREPEND everything you touch this script
###
ZWAVED_PREPEND = "-1"

ZWAVED_REVISION ?= "${DISTRO_PR}"
PKGR_${PN}-zwaved = "${PR}${ZWAVED_PREPEND}${ZWAVED_REVISION}"

RDEPENDS_${PN}-zwaved = "\
	python3-pkgutil \
	python3-pycrypto \
	python3-xml	\
"

do_install_append () {
        install -d ${D}/${INSTALL_DIR}/zwaved/
        ${RSYNC_CMD} ${S}/code/zwaved/ ${D}/${INSTALL_DIR}/zwaved/
        chmod +x ${D}/${INSTALL_DIR}/zwaved/zwaved.pyo

	# make zwaved/scripts executable
	chmod 0755 ${D}/${INSTALL_DIR}/zwaved/scripts/*
}

pkg_postinst_${PN}-zwaved () {
#!/bin/sh
# Post install to setup the home id directories
#
set -e

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

if [ -d /media/bootscript ]
then 
     logging "Check if bootscript is mounted..."
     bootscript_mountpoint=$(mount | grep bootscript | sed -e 's/[ ][ ]*/ /g' | cut -d' ' -f3)
     if [ -z "${bootscript_mountpoint}" ]
     then
          mount /dev/mmcblk0p3 /media/bootscript -o ro
     fi
     
     if [ -e /media/bootscript/serialnumber.txt ]
     then
          serial_number=$(cat /media/bootscript/serialnumber.txt)
          if [ -e /opt/2gig/zwaved/scripts/setup_home_id.pyo ]
          then
               logging "Setting up zwave home id..."
               python3 /opt/2gig/zwaved/scripts/setup_home_id.pyo -hid ${serial_number}
          else
               logging "setup_home_id.pyo does not exist, can not setup home id of "${serial_number}

          fi
     else
          logging "No serial number found, can not setup zwave home id"
     fi
else
     logging "No /media/bootscript directory, BAD BUILD."
fi

if [ ! -d /media/extra/log/error ]
then
     logging "Creating log directory..."
     mkdir -p /media/extra/log/error
fi
}


FILES_${PN}-zwaved = "\
	${INSTALL_DIR}/zwaved/*	\
	${sysconfdir}/init.d/zwaved		\
	${sysconfdir}/procman.d/zwaved		\
	"

