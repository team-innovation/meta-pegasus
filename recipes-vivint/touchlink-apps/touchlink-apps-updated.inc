###
### Change UPDATED_PREPEND everything you touch this script
###
UPDATED_PREPEND = "-3"

UPDATED_REVISION ?= "${DISTRO_PR}"
PKGR_${PN}-updated = "${PR}${UPDATED_PREPEND}${UPDATED_REVISION}"

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
        chmod +x ${D}/${INSTALL_DIR}/updated/updated.pyo
}

pkg_postinst_${PN}-updated () {
#!/bin/sh -e
# Post install to make sure we have the correct setup for updated
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

    device=/dev/mmcblk0p7
    # Check if we are ext3 and convert to ext4
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
    else
        # If not mounted and we detect ext3 we convert it to ext4
        if dumpe2fs -h $device | grep features | grep -qv extent
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
else
    exit 1
fi
}


FILES_${PN}-updated = "\
       ${INSTALL_DIR}/updated/*        \
	${sysconfdir}/init.d/updated		\
	${sysconfdir}/procman.d/updated	\
       "

             
