#!/bin/bash
set -x
# 
# Description: This script is intended to be used when doing ethboot to load
# code on a board with a blank eMMC chip.  It runs on the target device 
# partitioning, formatting, and copying files to the eMMC chip, destroying all 
# previous data on the eMMC chip.  The below table shows the resulting eMMC 
# chip.
#
# 1 BOOT 	- ext4 - contains u-boot
# 2 N/A 	- none - empty, reserved for env for u-boot
# 3 BOOTSCR	- ext4 - contains boot.scr for u-boot, or empty
# 4 N/A 	- extended partition for 5, 6, 7
# 5 ROOTFS1	- ext4 - contains rootfs1
# 6 ROOTFS2	- ext4 - empty, reserved for rootfs2
# 7 EXTRA 	- ext4 - empty, reserved for persistent data for apps
#
# Copyright (c) 2014 Vivint
#

# select debug output or not
DEBUG=yes
#DEBUG=no
QUIET=no
SCRIPTNAME=$(basename $BASH_SOURCE)
set -e

echo "$SCRIPTNAME"

BOARDNAME=slimline

carp() {
	echo "$SCRIPTNAME: fatalerror: $1"
	exit 1
}

usage() {
	carp "usage: $SCRIPTNAME root-device server-ipaddress"
}

info() {
	test "$QUIET" == "yes" ||
		echo "$SCRIPTNAME: info: $1"
}

debuginfo() {
	test "$DEBUG" == "yes" &&
		echo "$SCRIPTNAME: debuginfo: $1"
}

umount_drive () {
	for p in $(seq 7)
	do
		part="${DRIVE}p$p"
		umount "$part" > /dev/null 2>&1 && debuginfo "unmounted $part"
	done
	:
}


test $# == 2 ||
	usage

SNFILENAME=serialnumber.txt

# check board version
uname -a | grep -q "$BOARDNAME" ||
	carp "not a $BOARDNAME build, exiting..."

# get/check which drive to use and who our server is
DRIVE=$1
test "$DRIVE" ||
	carp "drive not specified"

test -b "$DRIVE" ||
	carp "drive is not a block device"

SERVERIP=$2
test "$SERVERIP" ||
	carp "serverip not specified"

ping -c 1 $SERVERIP > /dev/null 2>&1 ||
	carp "invalid serverip $SERVERIP"

# make sure drive is unmounted
debuginfo "Unmounting $DRIVE..."
umount_drive

# test that drive is there
dd if=/dev/zero of=$DRIVE bs=1024 count=1024 > /dev/null 2>&1 ||
	carp "Error: failed to dd to $DRIVE"

# partition drive
echo "Partitioning $DRIVE..."
SIZE=$(fdisk -l $DRIVE | grep -m 1 Disk | awk '{print $5}')
debuginfo "DISK SIZE - $SIZE bytes"

S=$((SIZE/1024/1024))
ROOTFS_SZ=793
if [ $S -le 1920 ]
then
	ROOTFS_SZ=397
fi
debuginfo "ROOTFS_SZ - $ROOTFS_SZ"

HEADS=128
SECTORS=32
SECTOR_SIZE=512
CYLINDERS=$((SIZE/HEADS/SECTORS/SECTOR_SIZE))
debuginfo "CYLINDERS - $CYLINDERS"

# partition starts and sizes in cylinders
P1SZ=36
NXT=$P1SZ
P2SZ=4
NXT=$(($NXT + $P2SZ))
P3SZ=4
NXT=$(($NXT + $P3SZ))
# we don't give p4 (extended) a size
# just increment NXT
NXT=$(($NXT + 1))
P5STRT=$NXT
P5SZ=$ROOTFS_SZ
NXT=$(($NXT + $P5SZ + 1))
P6STRT=$NXT
P6SZ=$ROOTFS_SZ
NXT=$(($NXT + $P5SZ + 1))
# p7 takes the rest
P7STRT=$NXT


#cat << EOF
sfdisk -f -q -D -L -H $HEADS -S $SECTORS -C $CYLINDERS $DRIVE << EOF
,$P1SZ,L
,$P2SZ,0xe3
,$P3SZ,L
,,E
$P5STRT,$P5SZ,L
$P6STRT,$P6SZ,L
$P7STRT,+,L
EOF

# create filesystems
echo "Making filesystems, please wait..."
mkfs.ext4 -qL BOOT    "$DRIVE"p1 > /dev/null 2>&1
mkfs.ext4 -qL BOOTSCR "$DRIVE"p3 > /dev/null 2>&1
mkfs.ext4 -qL ROOTFS1 "$DRIVE"p5 > /dev/null 2>&1
mkfs.ext4 -qL ROOTFS2 "$DRIVE"p6 > /dev/null 2>&1
mkfs.ext4 -qL DATA    "$DRIVE"p7 > /dev/null 2>&1
sync

# get serial number and write it to disk
while true; do
	clear; reset
	read -p "Enter Serialnumber: " SERIALNUMBER
	NUMCK=$(echo "$SERIALNUMBER" | awk '{print ($0 != $0+0)}')
	if [ "$NUMCK" -eq 0 ]; then
		break;
	fi
done

SNHEX=$(printf "%x" $SERIALNUMBER)
debuginfo "convert serialnumber to hex: $SNHEX"

mount "$DRIVE"p3 /mnt > /dev/null 2>&1
echo "$SNHEX" > /mnt/"$SNFILENAME"

echo "getting/programming files:"

# get boot script and write it to disk
debuginfo "boot.scr..."
cd /mnt
tftp -g -r boot.scr-imx6dl-slimline -l boot.scr $SERVERIP > /dev/null 2>&1
cd /
sync
umount /mnt > /dev/null 2>&1

# u-boot lives in the first eMMC boot partition /dev/mmcblk0boot0
debuginfo "eMMC uboot..."
# set up the eMMC device boot parameters

# this is confusing partially because you echo decimal values to the files
SYSFSDIR="/sys/devices/soc0/soc.1/2100000.aips-bus/219c000.usdhc/mmc_host/mmc2/mmc2:0001"

# set boot_bus to 10 decimal/ 0xa hex
# which is:
# boot_bus:0x0a
#   BOOT_MODE:1 - Use single data rate + high speed timings in boot operation mode
#   RESET_BOOT_BUS_WIDTH:0 - Reset bus width to x1, single data rate and backwardcompatible timings after boot operation
#   BOOT_BUS_WIDTH:2 - x8 (sdr/ddr) bus width in boot operation mode
#
echo -n 10 > ${SYSFSDIR}/boot_bus_config

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
#   wrong boot config parameter 00 (disable boot), 08 (enable boot1), 16 (enable boot2), 56 (User area)
#
# we want boot partition 1 so we use 8 which is the same for hex and decimal
echo -n 8 > ${SYSFSDIR}/boot_config

# now make boot partition 1 aka 0 writeable
# partition 1 is mmcblk0boot0
# partition 2 is mmcblk0boot1
echo 0 > /sys/block/mmcblk0boot0/force_ro

cd /tmp
tftp -g -r u-boot-imx6dl-slimline.imx -l u-boot.imx $SERVERIP > /dev/null 2>&1
dd if=/dev/zero of=/dev/mmcblk0boot0 bs=512 count=16
dd if=u-boot.imx of=/dev/mmcblk0boot0 bs=512 seek=2
sync
cd /

# u-boot env lives in the second eMMC boot sector
echo 0 > /sys/block/mmcblk0boot1/force_ro
dd if=/dev/zero of=/dev/mmcblk0boot1 bs=2M count=1

# get root fs
cd /tmp
debuginfo "root file system..."
mount "$DRIVE"p5 /mnt > /dev/null 2>&1
tftp -g -r rfs.tar.bz2 -l rfs.tar.bz2 $SERVERIP
tar xjf rfs.tar.bz2 -C /mnt > /dev/null 2>&1
sync
umount /mnt > /dev/null 2>&1
cd /

debuginfo "MKSD COMPLETE!"

exit 0
