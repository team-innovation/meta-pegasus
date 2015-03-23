DESCRIPTION = "U-Boot Script - Boot loader script loaded by u-boot for Sprouter"
HOMEPAGE = "http://www.vivint.com"
SECTION = "bootloaders"
PRIORITY = "optional"
LICENSE = "Closed"

DEPENDS = "u-boot-mkimage-native"
UBOOT_SCRIPT = "boot.scr"
SCR_MNT = "/media/bootscript"

COMPATIBLE_MACHINE = "imx6dl-sprouter"

SRC_URI = "git://git.vivint.com/uboot-imx.git;protocol=git;branch=master"
SRCREV = "${AUTOREV}"

inherit deploy

S = "${WORKDIR}/git"

PACKAGE_ARCH = "${MACHINE_ARCH}"

TARGET_BOARD ?= "Sprouter"

PR = "r8"

FILES_${PN} = "/boot"

do_mkimage () {
	uboot-mkimage -A arm -O linux -T script -C none -a 0 -e 0 \
		-n 'sprouter bootscript' \
		-d ${S}/board/vivint/mx6dlsprouter/bootscript.txt \
		${S}/${UBOOT_SCRIPT}
}

addtask mkimage after do_compile before do_install

do_deploy () {
    install -d ${DEPLOYDIR}
    install ${S}/${UBOOT_SCRIPT} \
            ${DEPLOYDIR}/${UBOOT_SCRIPT}-${MACHINE}-${PV}-${PR}

    cd ${DEPLOYDIR}
    rm -f ${UBOOT_SCRIPT}-${MACHINE}
    ln -sf ${UBOOT_SCRIPT}-${MACHINE}-${PV}-${PR} ${UBOOT_SCRIPT}-${MACHINE}
}

addtask deploy after do_install before do_build

do_install () {
	install -d ${D}/boot
	install ${S}/${UBOOT_SCRIPT} ${D}/boot/${UBOOT_SCRIPT}
	dd bs=1 skip=16 if=${D}/boot/${UBOOT_SCRIPT} | md5sum | awk '{ print $1 }' > ${D}/boot/${UBOOT_SCRIPT}.md5sum
}

do_configure[noexec] = "1"
do_compile[noexec] = "1"
do_populate_sysroot[noexec] = "1"

pkg_postinst_${PN} () {
#!/bin/sh
# We install the ${UBOOT_SCRIPT} into ${SCR_MNT}, the mount point must be mounted to /dev/mmcblk0p3
# depends on md5sum, awk, busybox, grep, mkdir, logger, mount. umount. mv, cp

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
 
 flash_script()
 {
 	scr_dev=/dev/mmcblk0p3
 	force_mount=0
 	remount=0
 	success=1
 	
 	# TODO - Should I check the u-boot version to match?
 	# would return the following ie: 2010.06-svn9379
 	# uboot_ver=$(fw_printenv version | cut -d'=' -f 2 | awk '{ print $2 }')
 	
 	logging "Check install of u-boot bootscript"
 	
  	# Get the checksum of the new script file
 	script_checksum=$(cat /boot/${UBOOT_SCRIPT}.md5sum)
 	
 	# Check if we should replace as if it is the same do nothing.
 	sum=$(dd bs=1 skip=16 if=${SCR_MNT}/${UBOOT_SCRIPT} | md5sum  | awk '{print $1}')
 	
 	if [ "${sum}" == "${script_checksum}" ]
 	then
 		logging "Bootscript already installed"
 		exit 0
 	fi 

 	board=$(fw_printenv board | cut -d'=' -f 2)
 	if [ "${board}" != "${TARGET_BOARD}" ]
 	then
 		logging "u-boot board type does not match, found ${board} expecting ${TARGET_BOARD}, can not install boot script."
 		exit 0
 	fi 
 	
 	logging "Installing u-boot bootscript"
 
 	if mount | grep -q ${scr_dev}
 	then
 		logging "Remounting partition for bootscript"
 		mount ${scr_dev} ${SCR_MNT} -o remount,rw,sync
 		remount=1
 	else
 		logging "Mounting partition for bootscript"
 		mount ${scr_dev} ${SCR_MNT} -o rw,sync
 		force_mount=1
 	fi
 	
 	logging "Copying to script partition"
 	cp /boot/${UBOOT_SCRIPT} ${SCR_MNT}/${UBOOT_SCRIPT}.tmp
 	
 	logging "Calculating Checksum for Bootscript"
 	sum=$(dd bs=1 skip=16 if=${SCR_MNT}/${UBOOT_SCRIPT}.tmp | md5sum | awk '{print $1}')
 	if [ "${sum}" == "${script_checksum}" ]
 	then
 		logging "Boot script checksum success, replaced"
 		mv ${SCR_MNT}/${UBOOT_SCRIPT}.tmp ${SCR_MNT}/${UBOOT_SCRIPT}
 		success=1
 	else
 		logging "Boot script checksum failed"
 		rm ${SCR_MNT}/${UBOOT_SCRIPT}.tmp
 		success=0 	
 	fi
 	
 	if [ ${force_mount} -eq 1 ]
 	then
 		logging "Umounting script partition"
 		umount ${scr_dev}
 	elif [ ${remount} -eq 1 ]
 	then
 		logging "Remounting script partition"
 		mount ${scr_dev} ${SCR_MNT} -o remount,ro
 	fi
 	
 	if [ ${success} -eq 0 ]
 	then
 		logging "Failed to install bootscript"
 		# TODO - Indicate some place so we can display failures on the GUI?
 		sleep 10
 	else
 		logging "Boot script installation complete!, need to reboot when complete."
		date > /.reboot
 	fi
 	
 }
 

flash_script

}
