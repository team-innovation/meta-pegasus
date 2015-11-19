#!/bin/bash
set -x
# Copyright (c) 2015 Vivint
# Description: see slimline-emmc-prefs-init.sh for full details.
#
#

# select debug output or not
DEBUG=yes
#DEBUG=no
QUIET=no
SCRIPTNAME=$(basename $BASH_SOURCE)
set -e

echo "$SCRIPTNAME"

carp() {
	echo "$SCRIPTNAME: fatalerror: $1"
	exit 1
}

debuginfo() {
	test "$DEBUG" == "yes" &&
		echo "$SCRIPTNAME: debuginfo: $1"
}

readbid() {
	local bid=$(cat /sys/fsl_otp/HW_OCOTP_GP1)
	bid=$(printf 0x%08x $bid)
	echo -n ${bid#0x??????}
}

writebid() {
	local bid=$1

	# the fuses are write once and start out as all zero
	# bits can be set but not cleared so writing a zero to any bit has no effect
	# this means there is no need to preserve upper bits
	# the byte we want to write is the least significant so no
	# shift is needed either

	# just make sure the bid is of the form 0xXX
	bid=$(printf 0x%2x $bid)
	echo -n ${bid} > /sys/fsl_otp/HW_OCOTP_GP1
}

slimlinesetbid() {
	local currentbid=$(readbid)

	case $currentbid in
		00 )	# new board with fuse never set
			writebid 0x01
			;;
		01 )	# already set, nothing todo
			echo "bid is already set, all good!"
			;;
		*)
			carp "bogus current bid ${currentbid} for slimline"
			;;
	esac
	true
}

saneslybid() {
	local bid=$1
	local ttydev=$(tty)

	test $bid = "02" -a $ttydev = "/dev/ttymxc1" &&
		return 0
	test $bid = "03" -a $ttydev = "/dev/ttymxc2" &&
		return 0
	test $bid = "19" -a $ttydev = "/dev/ttymxc1" &&
		return 0
	return 1
}

slysetbid() {
	local currentbid=$(readbid)

	case $currentbid in
		00 )	# new board with fuse never set
			saneslybid 0x03 ||
				carp "refusing to write bid '03' with tty mismatch '$(tty)'"
			writebid 0x03
			;;
		02 | 03 | 19 )	# already set, just do a tty vs bid sanity check
			saneslybid $currentbid ||
				carp "current bid '$bid' mismatch with ttydev '$(tty)'"
			echo "bid is already set, all good!"
			;;
		*)
			carp "bogus current bid ${currentbid} for sly"
			;;
	esac
	true
}

setbid() {
	if grep -q vivint,slimline /proc/device-tree/compatible; then
		slimlinesetbid
	elif grep -q vivint,sly /proc/device-tree/compatible; then
		slysetbid
	else
		carp "unknown boardname from device tree"
	fi
}

setbid

# TODO: make functions of the rest of this script

# the pre-fsextract script has been run and the rootfs has been extracted
# into /dev/mmcblock0p5 which should be mounted on /mnt/mmcblock0p5
# all the files needed to complete installation should be in the extracted rootfs

##
# boot script

mkdir -p /mnt/mmcblk0p3
mount /dev/mmcblk0p3 /mnt/mmcblk0p3
cp /mnt/mmcblk0p5/boot/boot.scr /mnt/mmcblk0p3
sync
umount /dev/mmcblk0p3

##
# i.mx6 and eMMC boot settings

# u-boot lives in the first eMMC boot partition /dev/mmcblk0boot0
debuginfo "eMMC uboot..."
# set up the eMMC device boot parameters

# this is confusing partially because you echo decimal values to the files
SYSFSDIR=$(find /sys/devices -type d | grep 'mmc.:0001$')

# make boot partition 1 aka 0 writeable
# partition 1 is mmcblk0boot0
# partition 2 is mmcblk0boot1
echo 0 > /sys/block/mmcblk0boot0/force_ro

# set boot_bus to 10 decimal/ 0xa hex
# which is:
# boot_bus:0x0a
#   BOOT_MODE:1 - Use single data rate + high speed timings in boot operation mode
#   RESET_BOOT_BUS_WIDTH:0 - Reset bus width to x1, single data rate and
#                            backwardcompatible timings after boot operation
#   BOOT_BUS_WIDTH:2 - x8 (sdr/ddr) bus width in boot operation mode
#
sleep 1
echo 10 > ${SYSFSDIR}/boot_bus_config

# choose the boot partition, we use boot partition 1 of 2
# this is confusing again because of hex/decimal
# from the source we have this comment:
#
# 0x00 - disable boot enable.
# 0x08 - boot partition 1 is enabled for boot.
# 0x10 - boot partition 2 is enabled for boot.
# 0x38 - User area is enabled for boot.
#
# If you do the right stupid thing you might get this helpful message
#   wrong boot config parameter 00 (disable boot),
#                               08 (enable boot1),
#                               16 (enable boot2),
#                               56 (User area)
#
# we want boot partition 1 so we use 8 which is the same for hex and decimal
sleep 1
echo 8 > ${SYSFSDIR}/boot_config

# display the resulting info as a sanity check
sleep 1
cat ${SYSFSDIR}/boot_info

# finally copy u-boot into the boot partition
dd if=/dev/zero of=/dev/mmcblk0boot0 bs=512 count=16
dd if=/mnt/mmcblk0p5/boot/u-boot.imx of=/dev/mmcblk0boot0 bs=512 seek=2
sync

# u-boot env lives in the second eMMC boot sector
echo 0 > /sys/block/mmcblk0boot1/force_ro
dd if=/dev/zero of=/dev/mmcblk0boot1 bs=2M count=1

## blow the i.mx6 boot fuses necessary to boot from eMMC

#
# BOOT_CONFIG1[7:6] = 01  - Boot from USDHC Interfaces
# BOOT_CONFIG1[5]   = 1   - MMC/eMMC
# BOOT_CONFIG1[4]   = 1   - Fast Boot
#
# BOOT_CONFIG1[7 6 5 4 3 2 1 0]
#              0 1 1 1 0 0 0 0 = 0x70
#
# BOOT_CONFIG2[7:5] = 010 - 8-bit
# BOOT_CONFIG2[4:3] = 11  - USDHC-4
#
# BOOT_CONFIG2[7 6 5 4 3 2 1 0]
#              0 1 0 1 1 0 0 0 = 0x58

# HW_OCOTP_CFG4 contains [boot_config4:boot_config3:boot_config2:boot_config1]
echo 0x5870 > /sys/fsl_otp/HW_OCOTP_CFG4
cat /sys/fsl_otp/HW_OCOTP_CFG4

# BT_FUSE_SEL is bit 4 (1 << 4 == 0x10) in HW_OCOTP_CFG5
# this bit is what tells the core to use the other boot fuses
echo 0x10 > /sys/fsl_otp/HW_OCOTP_CFG5
cat /sys/fsl_otp/HW_OCOTP_CFG5

# Done with i.mx6 boot fuse setup

## one last thing update the psoc firmware if needed
# kernel firmware loader expects data in /lib/firmware
cp -a /mnt/mmcblk0p5/lib/firmware /lib/firmware
# rest is nearly automagic
echo 1 > /sys/class/swd/psoc50/doall

debuginfo "Initial slimline setup complete!"

exit 0
