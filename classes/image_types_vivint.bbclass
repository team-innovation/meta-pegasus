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
# by sfdisk -l -u S is:
#
#Disk /dev/mmcblk0: 238592 cylinders, 4 heads, 16 sectors/track
#sfdisk: Warning: The partition table looks like it was made
#  for C/H/S=*/128/32 (instead of 238592/4/16).
#For this listing I'll assume that geometry.
#Units: sectors of 512 bytes, counting from 0

#   Device Boot    Start       End   #sectors  Id  System
#/dev/mmcblk0p1            32    147455     147424  83  Linux
#/dev/mmcblk0p2        147456    163839      16384  e3  DOS R/O
#/dev/mmcblk0p3        163840    180223      16384  83  Linux
#/dev/mmcblk0p4        180224  15269887   15089664   5  Extended
#/dev/mmcblk0p5        184320   3432447    3248128  83  Linux
#/dev/mmcblk0p6       3436544   6684671    3248128  83  Linux
#/dev/mmcblk0p7       6688768  15269887    8581120  83  Linux
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

## cylinder to bytes
#
ctob() {
    expr $1 \* $HEADS \* $SECTORS \* $SECTOR_SIZE
}

## bytes to cylinder
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
## sectors to bytes
#
stob() {
    expr $1  \* $SECTOR_SIZE
}

## bytes to sectors
#    with sanity check make sure all sizes are sector aligned
#
btos() {
    local sz=$1
    local nsz

    szsec=$(expr $sz / $SECTOR_SIZE )
    nsz=$(expr $szsec \* $SECTOR_SIZE )
    test "$sz" -eq "$nsz" || bberror "bad size not sector aligned"
    echo $szsec
}

## setup all sizes in both bytes and sectors
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

    # starts and sizes in sectors of all partitions
    # mbr/partition table are in 0 so start partition at next sector
    local NXTSEC=$SECTORS

    export P1STRT_SEC=$NXTSEC
    export P1STRT=$(stob $P1STRT_SEC)
    export P1SZ_SEC=$(expr `btos $P1SZ` - $SECTORS)
    NXTSEC=$(expr $NXTSEC + $P1SZ_SEC )

    export P2STRT_SEC=$NXTSEC
    export P2STRT=$(stob $P2STRT_SEC)
    export P2SZ_SEC=$(btos $P2SZ)
    NXTSEC=$(expr $NXTSEC + $P2SZ_SEC )

    export P3STRT_SEC=$NXTSEC
    export P3STRT=$(stob $P3STRT_SEC)
    export P3SZ_SEC=$(btos $P3SZ)
    NXTSEC=$(expr $NXTSEC + $P3SZ_SEC )

    # we don't give p4 (extended) a size
    # just increment NXTSEC
    export P4STRT_SEC=$NXTSEC
    NXTSEC=$(expr $NXTSEC + $HEADS \* $SECTORS)

    export P5STRT_SEC=$NXTSEC
    export P5STRT=$(stob $P5STRT_SEC)
    export P5SZ_SEC=$(btos $P5SZ)
    # partitions in extended store info inline so add
    # one extra cylinder
    NXTSEC=$(expr $NXTSEC + $P5SZ_SEC + $HEADS \* $SECTORS )

    export P6STRT_SEC=$NXTSEC
    export P6STRT=$(stob $P6STRT_SEC)
    export P6SZ_SEC=$(btos $P6SZ)
    # partitions in extended store info inline so add
    # one extra cylinder
    NXTSEC=$(expr $NXTSEC + $P6SZ_SEC + $HEADS \* $SECTORS )

    # p7 takes the rest
    export P7STRT_SEC=$NXTSEC
    export P7STRT=$(stob $P7STRT_SEC)
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
    sfdisk -f -q  $EMMC << EOF
$P1STRT_SEC,$P1SZ_SEC,L
$P2STRT_SEC,$P2SZ_SEC,0xe3
$P3STRT_SEC,$P3SZ_SEC,L
$P4STRT_SEC,,E
$P5STRT_SEC,$P5SZ_SEC,L
$P6STRT_SEC,$P6SZ_SEC,L
$P7STRT_SEC,+,L
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

    cp ${EMMC_ROOTFS} \
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
    local extrad="${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.$fsname.d/"
    local gzname="${IMGDEPLOYDIR}/${IMAGE_NAME}.rootfs.tar.gz"
    mkdir -p $extrad/factory_image
    cp $gzname $extrad/factory_image
    dd if=/dev/zero \
	of=${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.$fsname.ext4 \
	bs=1 \
	count=0 \
	seek=$size
    mkfs.ext4 -F ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.$fsname.ext4 \
	-L $volname -d $extrad 
    dd if=${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.$fsname.ext4 \
	of=$EMMC conv=notrunc \
	bs=$(ctob 1) \
	seek=$(btoc $start) \
	count=$(btoc $size)

    :
}

do_image_emmc[depends] += "util-linux-native:do_populate_sysroot \
                        dosfstools-native:do_populate_sysroot \
                        mtools-native:do_populate_sysroot \
                        virtual/kernel:do_deploy \
			"

export EMMC = "${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.image.emmc"

EMMC_GENERATION_COMMAND = "generate_slimline_emmc"
#EMMC_GENERATION_COMMAND_monkeyv2 = "generate_monkeyv2_emmc"

EMMC_ROOTFS ?= "${IMGDEPLOYDIR}/${IMAGE_NAME}.rootfs.ext4"

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
    f=$(basename ${EMMC})
    z=${f}.zip
    d=$(dirname ${EMMC})
    (cd ${d}; zip ${z} ${f}; rm ${f}; sha256sum ${z} > ${z}.sha256)
}

# The emmc requires the rootfs filesystem to be built before using
# it so we must make this dependency explicit.
IMAGE_TYPEDEP_emmc = "${@d.getVar('EMMC_ROOTFS', 1).split('.')[-1]}"
