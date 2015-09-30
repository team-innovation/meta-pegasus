inherit image_types

# Vivint eMMC images have multiple partitions but only the first rootfs
# partition is populated here.  Others have mkfs run on them or are left
# unused.  Everything else happens on first boot.  See the firstboot utility
# for details, here is a synopsis.
#
# U-boot resides in the emmcboot partition 1 and u-boot env in 2:
# /dev/mmcblk0boot0
# /dev/mmcblk0boot1
#
# Note: these are not partitions in the msdos partition table sense
# but special regions of the eMMC device set asside for boot info.
#
# The firstboot script copies u-boot binary from rootfs/boot/u-boot.imx
# to /dev/mmcblk0boot0 and clears u-boot env in /dev/mmcblk0boot1.
#
# Slimline partition table details:
#
# The actual msdos partition table defined partitioning as represented
# by sfdisk -l is:
#
# Disk /dev/mmcblk0: 238592 cylinders, 4 heads, 16 sectors/track
# sfdisk: Warning: The partition table looks like it was made
#   for C/H/S=*/128/32 (instead of 238592/4/16).
# For this listing I'll assume that geometry.
# Units: cylinders of 2097152 bytes, blocks of 1024 bytes, counting from 0
#
# Device Boot Start     End   #cyls    #blocks   Id  System
# /dev/mmcblk0p1          0+     35      36-     73712   83  Linux
# /dev/mmcblk0p2         36      39       4       8192   e3  DOS R/O
# /dev/mmcblk0p3         40      43       4       8192   83  Linux
# /dev/mmcblk0p4         44    3727    3684    7544832    5  Extended
# /dev/mmcblk0p5         45     837     793    1624064   83  Linux
# /dev/mmcblk0p6        839    1631     793    1624064   83  Linux
# /dev/mmcblk0p7       1633    3727    2095    4290560   83  Linux
#
# p1 legacy, was u-boot on touchlink, not used here since we use mmcblk0boot0
# p2 legacy, was u-boot env not used here since we use mmcblk0boot1
# p3 is the bootscript partition described above
#   this script creates an empty ext4 fs here
# p4 is the extended partition holding space for table entries 5-7
# p5 is root partition 1 associated with bootnum=1 in u-boot env
#   this script creates an ext4 fs and populates it
# p6 is root partition 2 associated with bootnum=2 in u-boot env
#   nothing happens here for this partition, it will only be populated
#   on first update
# p7 is the extra partition and contains data that survives root fs changes
#   several files are created on first boot.  Only a mkfs happens in this
#   script.
#
# Monkeyv2 partition table details:
#
# TBD
#

export HEADS = "128"
export SECTORS = "32"
export SECTOR_SIZE = "512"

# two ways of calculating imagesize
# 1) kernel reports size as 7.28GiB
#   == 7.28*1024MiB == 7454.72
#   round down to next 2MiB size
#   7454MiB
# 2) /proc/partitions reports
#   179        0    7634944 mmcblk0
#   7634944/1024 == 7456MiB
# go with smallest to be safe
export IMAGESZ = "7454MiB"

# disk and partition sizes; could change but changes in other places
# may be necessary
export P1SZ = "72MiB"
export P2SZ = "8MiB"
export P3SZ = "8MiB"
export ROOTFSSZ = "1586MiB"

## nnnK, nnnM, nnnG to bytes
#
kmgtobytes() {
    local sz=$1
    case "$sz" in
	*GiB)
	    sz=$(expr ${sz%GiB} \* 1024 \* 1024 \* 1024)
	    ;;
	*MiB)
	    sz=$(expr ${sz%MiB} \* 1024 \* 1024)
	    ;;
	*KiB)
	    sz=$(expr ${sz%KiB} \* 1024)
	    ;;
    esac
    echo $sz
}

## cylinders to bytes
#
ctob() {
    expr $1 \* $HEADS \* $SECTORS \* $SECTOR_SIZE
}

## bytes to cylinders
#    with sanity check make sure all sizes are cylinder aligned
#
btoc() {
    local sz=$1
    local nsz

    szcyl=$(expr $sz / $HEADS / $SECTORS / $SECTOR_SIZE )
    nsz=$(expr $szcyl \* $HEADS \* $SECTORS \* $SECTOR_SIZE )
    test "$sz" -eq "$nsz" || bberror "bad size not cylinder aligned"
    echo $szcyl
}

## setup all sizes in both bytes and cylinders
#
setupsizes() {
    # expand sizes that are expressed in k, m or g
    IMAGESZ=$(kmgtobytes $IMAGESZ)
    P1SZ=$(kmgtobytes $P1SZ)
    P2SZ=$(kmgtobytes $P2SZ)
    P3SZ=$(kmgtobytes $P3SZ)
    ROOTFSSZ=$(kmgtobytes $ROOTFSSZ)
    P5SZ=$ROOTFSSZ
    P6SZ=$ROOTFSSZ

    # starts and sizes in cylinders of all partitions
    # mbr/partition table are in 0 so start partition at 1
    local NXTCYL=1

    export P1STRT_CYL=$NXTCYL
    export P1STRT=$(ctob $P1STRT_CYL)
    export P1SZ_CYL=$(btoc $P1SZ)
    NXTCYL=$(expr $NXTCYL + $P1SZ_CYL )

    export P2STRT_CYL=$NXTCYL
    export P2STRT=$(ctob $P2STRT_CYL)
    export P2SZ_CYL=$(btoc $P2SZ)
    NXTCYL=$(expr $NXTCYL + $P2SZ_CYL )

    export P3STRT_CYL=$NXTCYL
    export P3STRT=$(ctob $P3STRT_CYL)
    export P3SZ_CYL=$(btoc $P3SZ)
    NXTCYL=$(expr $NXTCYL + $P3SZ_CYL )

    # we don't give p4 (extended) a size
    # just increment NXTCYL
    export P4STRT_CYL=$NXTCYL
    NXTCYL=$(expr $NXTCYL + 1)

    export P5STRT_CYL=$NXTCYL
    export P5STRT=$(ctob $P5STRT_CYL)
    export P5SZ_CYL=$(btoc $P5SZ)
    # partitions in extended store info inline so add
    # one extra cylinder
    NXTCYL=$(expr $NXTCYL + $P5SZ_CYL + 1 )

    export P6STRT_CYL=$NXTCYL
    export P6STRT=$(ctob $P6STRT_CYL)
    export P6SZ_CYL=$(btoc $P6SZ)
    # partitions in extended store info inline so add
    # one extra cylinder
    NXTCYL=$(expr $NXTCYL + $P6SZ_CYL + 1 )

    # p7 takes the rest
    export P7STRT_CYL=$NXTCYL
    export P7STRT=$(ctob $P7STRT_CYL)
    export P7SZ=$(expr $IMAGESZ - $P7STRT)
    :
}

## use venerable sfdisk here should we switch to parted?
#
partitionimage() {
    #
    # NOTE: leave the heredoc below unindented
    #       sfdisk does not deal well with leading spaces
    #
    sfdisk -f -q -D -L -H $HEADS -S $SECTORS -C $(btoc $IMAGESZ) $EMMC << EOF
$P1STRT_CYL,$P1SZ_CYL,L
$P2STRT_CYL,$P2SZ_CYL,0xe3
$P3STRT_CYL,$P3SZ_CYL,L
$P4STRT_CYL,,E
$P5STRT_CYL,$P5SZ_CYL,L
$P6STRT_CYL,$P6SZ_CYL,L
$P7STRT_CYL,+,L
EOF
}

## Add an empty ext4 filesystem
#
addbootscrfs() {
    local fsname=$1
    local volname=$2
    local start=$3
    local size=$4
    local bootscrd="${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.$fsname.d"
    local bootscrname="${IMAGE_ROOTFS}/boot/boot.scr"

    mkdir -p $bootscrd
    cp $bootscrname $bootscrd
    dd if=/dev/zero \
	of=${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.$fsname.ext4 \
	bs=1 \
	count=0 \
	seek=$size
    mkfs.ext4 -F ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.$fsname.ext4 \
	-L $volname \
	-d $bootscrd
    dd if=${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.$fsname.ext4 \
	of=$EMMC conv=notrunc \
	bs=$(ctob 1) \
	seek=$(btoc $start) \
	count=$(btoc $size)
    :
}

## Add rootfs to image using previously generated root as starting point
#
addrootfs() {
    local fsname=$1
    # no volname since fs is already made
    local start=$3
    local size=$4

    cp "${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.rootfs.ext4" \
	${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.$fsname.ext4
    dd if=/dev/zero \
	of=${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.$fsname.ext4 \
	bs=1 \
	count=0 \
	seek=$size
    resize2fs -f ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.$fsname.ext4
    dd if=${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.$fsname.ext4 \
	of=$EMMC conv=notrunc \
	bs=$(ctob 1) \
	seek=$(btoc $start) \
	count=$(btoc $size)
    :
}

## extra partition needs a factory_image in it
#
addmediaextrafs() {
    local fsname=$1
    local volname=$2
    local start=$3
    local size=$4
    local extrad="${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.$fsname.d/factory_image"
    local gzname="${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.rootfs.tar.gz"
    mkdir -p $extrad/factory_image
    cp $gzname $extrad/factory_image
    dd if=/dev/zero \
	of=${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.$fsname.ext4 \
	bs=1 \
	count=0 \
	seek=$size
    mkfs.ext4 -F ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.$fsname.ext4 \
	-L $volname \
	-d $extrad
    dd if=${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.$fsname.ext4 \
	of=$EMMC conv=notrunc \
	bs=$(ctob 1) \
	seek=$(btoc $start) \
	count=$(btoc $size)

    :
}

IMAGE_DEPENDS_emmc = "util-linux-native:do_populate_sysroot \
                        dosfstools-native:do_populate_sysroot \
                        mtools-native:do_populate_sysroot \
                        virtual/kernel:do_deploy \
			"

export EMMC = "${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.image.emmc"

EMMC_GENERATION_COMMAND_slimline = "generate_slimline_emmc"
#EMMC_GENERATION_COMMAND_monkeyv2 = "generate_monkeyv2_emmc"

EMMC_ROOTFS ?= "${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.rootfs.ext4"

## generate an emmc card image file
#
generate_slimline_emmc () {
    partitionimage
    # boot0/u-boot bin not in image and initialized on first boot

    # boot1/u-boot env not in image and initialized on first boot

    # p1, legacy nothing to do here

    # p2, legacy nothing to do here

    # p3, bootscript, create ext4 fs of correct size, copy into image with dd
    addbootscrfs bootscr BOOTSCR $P3STRT $P3SZ

    # p4, extended nothing to do here

    # p5, rootfs the root already created by yocto is the wrong size
    # so copy the image to a new file, resize that file then use resize2fs
    # to fill the new file.
    addrootfs resizedrootfs ROOTFS1 $P5STRT $P5SZ

    # p6, other rootfs, will be created/populated on first update

    # p7, media extra which needs a factory rootfs image tarball
    addmediaextrafs media-extra MEDIAEXTRA $P7STRT $P7SZ
    :
}

IMAGE_CMD_emmc () {
    setupsizes
    # Initialize a sparse file
    dd if=/dev/zero \
        of=$EMMC \
	bs=1 \
	count=0 \
	seek=$IMAGESZ
    ${EMMC_GENERATION_COMMAND}
}

# The emmc requires the rootfs filesystem to be built before using
# it so we must make this dependency explicit.
IMAGE_TYPEDEP_emmc = "${@d.getVar('EMMC_ROOTFS', 1).split('.')[-1]}"
