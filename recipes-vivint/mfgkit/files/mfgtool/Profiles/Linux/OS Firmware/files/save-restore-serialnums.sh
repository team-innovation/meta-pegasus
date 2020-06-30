#!/bin/bash
set -x

DEBUG=yes
#DEBUG=no
QUIET=no
SCRIPTNAME=$(basename $BASH_SOURCE)
set -e

echo "$SCRIPTNAME"

BSPART=/dev/mmcblk0p3
BSMNTPT=/media/bootscript

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
		mount ${BSPART}
}

umntbootscript()
{
	mount | grep -q ${BSPART} &&
		umount ${BSPART}
}

mntbootscriptrw()
{
	umntbootscript || true
	mount ${BSPART} -o rw ||
		carp "unable to mount ${BSPART} read/write"
}

saveloc=/tmp/savedfiles

savestuff()
{
	mntbootscript
	mkdir -p $saveloc
	cp ${BSMNTPT}/id* $saveloc || true
	cp ${BSMNTPT}/*.txt $saveloc || true
	info "Done saving serial numbers etc."
	exit 0
}

restorestuff()
{
	mntbootscriptrw
	cp $saveloc/* ${BSMNTPT} || true
	info "Done restoring serial numbers etc."
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
