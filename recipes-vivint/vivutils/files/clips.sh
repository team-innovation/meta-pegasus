#!/bin/sh
# Copyright Vivint Innovation 2013
# Script creates a clips.img file to loop mount for the clips.  
# This way we can fix the available space for clips.
# The script also makes sure to mount.
# To auto mount add the following line to fstab (without the leading #)
# /media/extra/clips.img /media/clips       auto       loop                       0  0

. /etc/init.d/functions

STORAGE=/media/extra
IMAGE=clips.img
BS=4M
COUNT=0
SEEK=256
DD=/bin/dd
MKFS="/sbin/mkfs.ext2 -F -L CLIPS"
FSCK="/sbin/fsck.ext2 -p"
FSCK_Y="/sbin/fsck.ext2 -y"
TUNE2FS="/sbin/tune2fs -l"
MKDIR=/bin/mkdir
GREP=/bin/grep
MOUNT=/bin/mount
UMOUNT=/bin/umount
MV=/bin/mv
CP=/bin/cp
RM=/bin/rm
RMDIR=/bin/rmdir

logging()
{
    echo $*
    if [ ! -d /media/extra/update ]
    then
        mkdir -p /media/extra/update
    fi
    echo $(date) $* >> /media/extra/update/.update_status
    logger $*
}

import_fs()
{
     convert=0
     
     # Check if we are vfat
	$TUNE2FS $STORAGE/$IMAGE > /dev/null
	if [ $? -eq 1 ]
        then
		# Migrate to ext2
		logging "Migrating old vfat based img to ext2..."
		convert=1
	else
          # If we get here, check we are the right size
          file_size=`ls -l $STORAGE/$IMAGE | awk '{print $5}'`
          if [ $file_size -eq 262144 ]
          then
               # Too small let's make it bigger (increase to 1G)
               # TL-3559
               logging "Increase the clips.img from 256M to 1G..."
               convert=1
          fi
	fi
	
	if [ $convert -eq 1 ]
	then
		$MKDIR $STORAGE/clips.orig
		$MKDIR $STORAGE/clips.new
		$MV $STORAGE/$IMAGE $STORAGE/clips.old.img
		# Create a 1G clip partition
		$DD of=$STORAGE/$IMAGE bs=$BS seek=$SEEK count=$COUNT
		$MKFS $STORAGE/$IMAGE
		$MOUNT -o loop $STORAGE/clips.old.img $STORAGE/clips.orig
		$MOUNT -o loop $STORAGE/clips.img $STORAGE/clips.new
		$CP -a $STORAGE/clips.orig/* $STORAGE/clips.new/
		$UMOUNT $STORAGE/clips.orig
		$UMOUNT $STORAGE/clips.new
		$RM $STORAGE/clips.old.img
		$RMDIR $STORAGE/clips.orig
		$RMDIR $STORAGE/clips.new
	fi
}


if [ ! -d /media/clips ]
then
  logging "Create clips mountpoint..."
  $MKDIR /media/clips
fi

if [ ! -e $STORAGE/$IMAGE ]
then
  logging "Creating clips partition, please wait..."
  # 1G size
  $DD of=$STORAGE/$IMAGE bs=$BS seek=$SEEK count=$COUNT
  $MKFS $STORAGE/$IMAGE
else
  logging "$STORAGE/$IMAGE is already created..."
  import_fs
fi

if $GREP -q "\/media\/clips" /proc/mounts
then
    logging "Clips is already mounted..."
else
    logging "Mounting clips..."
    $MOUNT /media/clips
fi

exit 0
