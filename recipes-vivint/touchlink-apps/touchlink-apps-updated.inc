###
### Change UPDATED_PREPEND everything you touch this script
###
UPDATED_PREPEND = "-8"

UPDATED_REVISION ?= "${DISTRO_PR}"
PKGR_${PN}-updated = "${PR}${UPDATED_PREPEND}${UPDATED_REVISION}"

INITSCRIPT_PACKAGES += "${PN}-updated"
INITSCRIPT_NAME_${PN}-updated = "updated"
INITSCRIPT_PARAMS_${PN}-updated = "start 89 2 3 4 5 ."

RDEPENDS_${PN}-updated ="\
  rsync \
  e2fsprogs-tune2fs \
  e2fsprogs-e2fsck \
  e2fsprogs-mke2fs \
  u-boot-fw-utils \
  opkg \
  python3-psutil \
  python3-compression \
  python3-gnupg \
  python3-misc \
  python3-image \
  python3-email \
  watchdog \
  bzip2 \
  gzip \
  zsync \
"
do_install_append () {
	install -d ${D}/${INSTALL_DIR}/updated/
        ${RSYNC_CMD} --exclude=docs --exclude=*.cmd ${S}/code/updated/ ${D}/${INSTALL_DIR}/updated/
        chmod +x ${D}/${INSTALL_DIR}/updated/updated.pyc
	install -d "${D}${sysconfdir}/logrotate.d"
	install -m 0600 "${WORKDIR}/updated.logrotate" "${D}${sysconfdir}/logrotate.d/updated" 
}

pkg_postinst_ontarget_${PN} () {
#!/bin/sh -e
# Post install to make sure we have the correct setup for updated
#

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

    device=/dev/mmcblk0p7
    # Check if we are ext3 and convert to ext4
    if mount | grep -q "/media/extra"
    then 
        if mount | grep "/media/extra" | awk '{print $5}' | grep -q ext3
        then
            echo "Converting mounted $device ext3 to ext4..."
            device=$(mount | grep "/media/extra" | awk '{print $1}')
            if umount $device
            then
                tune2fs -O extents,uninit_bg,dir_index $device
                e2fsck -fDC0 -y $device
                mount $device /media/extra
            else
                echo "Failed to unmount "$device
            fi
        fi
    else
        # If not mounted and we detect ext3 we convert it to ext4
        if ! dumpe2fs -h $device | grep features | grep -q extent
        then
            echo "Converting $device ext3 to ext4..."
            tune2fs -O extents,uninit_bg,dir_index $device
            e2fsck -fDC0 -y $device
        fi
    fi
     
    if [ ! -d /media/extra/update ]
    then
        logging "Creating update directory..."
        mkdir /media/extra/update
    fi

    # Make sure usb_update exists
    echo 0 > /sys/block/mmcblk0boot1/force_ro
    fw_printenv usb_update 2&> /dev/null || fw_setenv usb_update no
    echo 1 > /sys/block/mmcblk0boot1/force_ro

	# Copy SSH identity and authorized key files

	eval $(fw_printenv bootnum || echo bootnum=1)
	case $bootnum in
		1) previous_bootmount=/media/mmcblk0p6 ;;
		2) previous_bootmount=/media/mmcblk0p5 ;;
		*) ;;
	esac

    old_ssh_config_dir=$previous_bootmount/etc/ssh
    if [ -d $old_ssh_config_dir ]
    then
        for f in $(cd $previous_bootmount/etc/ssh/ && find . -maxdepth 1 -type f -name \*key\*) ; do
            cp $old_ssh_config_dir/$f /etc/ssh/
            logging "Copied $old_ssh_config_dir/$f to /etc/ssh/"
        done
    fi
    old_root_ssh_config=$previous_bootmount/home/root/.ssh
    if [ -d $old_root_ssh_config ]
    then
        install $old_root_ssh_config /home/root/
        logging "Copied $old_root_ssh_config to /home/root/"
    fi

}


FILES_${PN}-updated = "\
       ${INSTALL_DIR}/updated/*        \
	${sysconfdir}/init.d/updated		\
	${sysconfdir}/procman.d/updated	\
	${sysconfdir}/logrotate.d/updated	\
       "

