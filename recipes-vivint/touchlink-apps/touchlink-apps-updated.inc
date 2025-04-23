SYSTEMD_SERVICE_${PN}-updated = "updated.service updated-restore-dbs.service"
SYSTEMD_AUTO_ENABLE_${PN}-updated = "enable"

RDEPENDS_${PN}-updated ="\
  rsync \
  e2fsprogs-tune2fs \
  e2fsprogs-e2fsck \
  e2fsprogs-mke2fs \
  u-boot-imx-fw-utils \
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
  lsof \
  zsync \
"

do_install_append () {
	install -d ${D}/${INSTALL_DIR}/updated/
    ${RSYNC_CMD} --exclude=docs --exclude=*.cmd ${S}/code/updated/ ${D}/${INSTALL_DIR}/updated/
	install -d ${D}/usr/local/bin
	install -m 0755 ${S}/config/init.d/updated-restore-dbs ${D}/usr/local/bin
    chmod +x ${D}/${INSTALL_DIR}/updated/updated.pyc
	install -d "${D}${sysconfdir}/logrotate.d"
	install -m 0600 "${WORKDIR}/updated.logrotate" "${D}${sysconfdir}/logrotate.d/updated" 
	install -m 0600 "${WORKDIR}/segfault.logrotate" "${D}${sysconfdir}/logrotate.d/segfault" 

	# release note for release build only
    install -d ${D}/usr/share/doc
    if [ ${INSTALL_RELEASE_NOTE} = "TRUE" ] ; then
        install -m 0600 ${S}/config/release_notes.json ${D}/usr/share/doc
	else
        # prevent build error
        touch ${D}/usr/share/doc/release_notes.json
    fi

	# systemd services
    install -d ${D}${systemd_unitdir}/system
    install -m 0644 ${WORKDIR}/systemd/updated.service ${D}${systemd_unitdir}/system
    install -m 0644 ${WORKDIR}/systemd/updated-restore-dbs.service ${D}${systemd_unitdir}/system
}

pkg_postinst_ontarget_${PN}-updated () {
    #!/bin/sh -e
    # Post install to make sure we have the correct setup for updated

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

    set_force_ro() {
        if [ -e /sys/block/mmcblk0boot1/force_ro ]; then
        echo $1 > /sys/block/mmcblk0boot1/force_ro
            elif [ -e /sys/block/mmcblk2boot1/force_ro ]; then
        echo $1 > /sys/block/mmcblk2boot1/force_ro
        fi
    }

    set_previous_bootmount() {
        cur_bootmount=$(cat /proc/cmdline | awk '{print $2}' | cut -d/ -f3)

        if [ $cur_bootmount = "mmcblk2p2" ]; then
            previous_bootmount=/media/mmcblk2p3
        elif [ $cur_bootmount = "mmcblk2p3" ]; then
                previous_bootmount=/media/mmcblk2p2
        elif [ $cur_bootmount = "mmcblk0p5" ]; then
                previous_bootmount=/media/mmcblk0p6
            elif [ $cur_bootmount = "mmcblk0p6" ]; then
                previous_bootmount=/media/mmcblk0p5
        else
                previous_bootmount="UNKNOWN"
        fi
    }

    ### This is for older devices
    if [ -e /dev/mmcblk0p7 ] ; then
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
    fi
     
    if [ ! -d /media/extra/update ]
    then
        logging "Creating update directory..."
        mkdir -p /media/extra/update
    fi

    # Make sure usb_update exists
    set_force_ro 0
 
    fw_printenv usb_update 2&> /dev/null || fw_setenv usb_update no
    set_force_ro 1
    
    # Copy SSH identity and authorized key files
    set_previous_bootmount

    if [ $previous_bootmount != "UNKNOWN" ]; then
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
           cp --update -a $old_root_ssh_config /home/root/
           logging "Copied $old_root_ssh_config to /home/root/"
        fi
    fi
}

FILES_${PN}-updated = "\
    ${INSTALL_DIR}/updated/* \
	${sysconfdir}/init.d/updated \
	${sysconfdir}/procman.d/updated	\
	${sysconfdir}/logrotate.d/updated \
	${sysconfdir}/logrotate.d/segfault \
    ${systemd_unitdir}/system/* \
	/usr/share/doc/* \
    /usr/local/bin/updated-restore-dbs \
"
