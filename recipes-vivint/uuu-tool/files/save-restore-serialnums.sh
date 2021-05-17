#!/bin/bash
set -x

DEBUG=yes
#DEBUG=no
QUIET=no
SCRIPTNAME=$(basename $BASH_SOURCE)
set -e

echo "$SCRIPTNAME"

# a. This will pick the lowest index emmc block device
# EMMCDEV=$(ls /dev/mmcblk[0-9] | head -n1 | awk '{print $1;}')
# b. Hardcode
EMMCDEV=/dev/mmcblk2

BSLABEL=bootscript

# re-read partition table
blockdev --rereadpt -v $EMMCDEV

# find by label = partition number independent
BSPART=$(findfs LABEL=$BSLABEL || true)
BSMNTPT=/media/$BSLABEL

# Utility functions
carp() {
	echo "$SCRIPTNAME: fatalerror: $1"
	exit 1
}

usage() {
	carp "usage: $SCRIPTNAME save|restore"
}

info() {
	test "$QUIET" == "yes" ||
		echo "$SCRIPTNAME: $1"
}

debuginfo() {
	test "$DEBUG" == "yes" &&
		echo "$SCRIPTNAME: debug: $1"
}

#
# The bootscript and serial number and other stuff we sometimes
# want to preserve are in the bootscript partition
#

# Some functions for (un)mounting the boot script partition
# read-only and read/write
#
mntbootscript()
{
	mount | grep -q ${BSPART} ||
		mount ${BSPART} ${BSMNTPT}
}

umntbootscript()
{
	mount | grep -q ${BSPART} &&
		umount ${BSPART}
}

mntbootscriptrw()
{
	umntbootscript || true
	mount ${BSPART} ${BSMNTPT} -o rw ||
		carp "unable to mount ${BSPART} read/write"
}

saveloc=/tmp/savedfiles

savestuff()
{
	if [ -e "$BSPART" ]; then
		mntbootscript
		mkdir -p $saveloc
		#cp ${BSMNTPT}/* $saveloc || true
		# don't need all
		cp ${BSMNTPT}/id* $saveloc || true
		cp ${BSMNTPT}/*.txt $saveloc || true
		cp ${BSMNTPT}/*.bin $saveloc || true
		#tar cvf $saveloc.tar -C ${BSMNTPT} $(ls ${BSMNTPT}) || true
		tar cvf $saveloc.tar -C $saveloc $(ls $saveloc) || true
		umntbootscript
		if [ -e $saveloc.tar ]; then
			info "Done saving serial numbers etc.."
		else
			info "Done. Nothing to backup."
		fi
	else
		info "The parition does not exist. Storing serial numbers failed."
	fi
	touch $saveloc.tar
	exit 0
}

restorestuff()
{
	if [ -e "$BSPART" ]; then
		mntbootscriptrw
		cp $saveloc/* ${BSMNTPT} || true
		umntbootscript
		info "Done restoring serial numbers etc.."
	else
		info "The parition does not exist. Nothing to be restored."
	fi
	exit 0
}

cmd=$1
test -n "$cmd" || usage

case $cmd in
	save)
		savestuff
		;;
	restore)
		restorestuff
		;;
	*)
		carp "unrecognized command $cmd, expected 'save' or 'restore'"
		;;
esac
exit 0
