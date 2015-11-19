#!/bin/bash
# Copyright (c) 2015 Vivint
set -x
#
# Description: This script is intended to be used when doing ethboot to load
# code on a board with a blank or corrupted eMMC chip.  It runs on the target
# device:
#   partition emmc
#   run mkfs on various partitions
#
# This script is run from the uuc tool from the fsl mfg tool. 
#
# When complete the mfg tool downloads filesystem image via the "pipe"
# command sending output to tar with appropriate arguments to populate
# /dev/mmcblk0p5.  After the filesystem is populated, a post fs script
# is run to finish:
#   set boot fuses on i.mx6
#   set cooresponding boot settings in emmc
#   copy u-boot.imx from freshly extracted rootfs to emmc boot partition 1
#   copy u-boot boot script from rootfs to file in boot script partition
#   zero out u-boot env in emmc boot partition 2
#   sync and halt
#
# 1 BOOT	- ext4 - contains u-boot
# 2 N/A		- none - empty, reserved for env for u-boot
# 3 BOOTSCR	- ext4 - contains boot.scr for u-boot, or empty
# 4 N/A		- extended partition for 5, 6, 7
# 5 ROOTFS1	- ext4 - contains rootfs1
# 6 ROOTFS2	- ext4 - empty, reserved for rootfs2
# 7 EXTRA	- ext4 - empty, reserved for persistent data for apps
#
#

## These could be parameters, hardcoded here for now
export DRIVE=/dev/mmcblk0

## This should never change

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

info() {
	test "$QUIET" == "yes" ||
		echo "$SCRIPTNAME: info: $1"
}

debuginfo() {
	test "$DEBUG" == "yes" &&
		echo "$SCRIPTNAME: debuginfo: $1"
}

umount_all_partitions () {
	for p in $(seq 7)
	do
		part="${DRIVE}p$p"
		umount "$part" > /dev/null 2>&1 && debuginfo "unmounted $part"
	done
	:
}

export BOARDNAME=unknown
grep -q vivint,slimline /proc/device-tree/compatible &&
	export BOARDNAME=slimline
grep -q vivint,sly /proc/device-tree/compatible &&
	export BOARDNAME=sly

test $BOARDNAME = "unknown" &&
	carp "unknown boardname, exiting..."

# get/check which drive to use and who our server is
test "$DRIVE" ||
	carp "drive not specified"

test -b "$DRIVE" ||
	carp "drive is not a block device"

# make sure drive is unmounted
debuginfo "Unmounting $DRIVE..."
umount_all_partitions

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

exit 0
