DESCRIPTION = "Awful hack to build zip of files for manufacturing"

inherit meta

LICENSE = "CLOSED"

DEPLOY_DIR_MFG = "${DEPLOY_DIR}/mfgkit"

latest() {
	local match=$1 # an re not a glob
	local dir=$2

	f=$(ls -art ${dir} | grep ${match} | tail -n 1)
	echo ${dir}/${f}
}

# HACK do_compile is the one required step of a recipe so thats what we call this
do_compile() {
	tmpdir=$(mktemp -d)
	mkdir ${tmpdir}/staging
	mfgimagename=${platform}-mfgkit-${DISTRO_VERSION}
	ddir=${DEPLOY_DIR_MFG}/${mfgimagename}
	mkdir -p ${ddir}

	# get emmc image from image deploy directory
	mkdir -p ${ddir}/emmcimage/
	f=$(latest '${platform}-qt5-image.*image.emmc.zip$' ${DEPLOY_DIR_IMAGE})
	cp ${f} ${ddir}/emmcimage
	cp ${f}.sha256 ${ddir}/emmcimage

	# copy mfgtool tree from recipe files sub-directory
	rsync -a --exclude '*~' ${FILE_DIRNAME}/files/mfgtool ${ddir}

	# copy the mfgtool compatible initramfs
	cp ${DEPLOY_DIR_IMAGE}/${platform}-image-mfgtool-initramfs-imx6dl-slimline.cpio.gz.u-boot ${tmpdir}/staging/

	# the mfgtool kernel recipes has its own deploy which copies a
	# zImage to the image deploy directory so copy that zimage
	cp ${DEPLOY_DIR_IMAGE}/zImage_mfgtool ${tmpdir}/staging/zImage

	# get the rest of the files from the qt5 rootfs tarball /boot directory
	#   u-boot
	#   nofec version of slimline dtb
	#   sly dtb's
	#   special reflash u-boot boot script
	#   
	# all the files we need are relative symlinks so cp with -L will convert
	# the links to files
	tar  xvf ${DEPLOY_DIR_IMAGE}/${platform}-qt5-image-imx6dl-slimline.tar.gz -C ${tmpdir} --wildcards ./boot/
	cp -L ${tmpdir}/boot/u-boot.imx ${tmpdir}/staging/
	cp -L ${tmpdir}/boot/imx6dl-slimline-ldo-nofec.dtb  ${tmpdir}/staging/
	cp -L ${tmpdir}/boot/imx6dl-sly*.dtb  ${tmpdir}/staging/
	cp -L ${tmpdir}/boot/usb-reflash-boot.scr ${tmpdir}/staging/

	# copy from staging area to files directory in mfgtool tree
	cp ${tmpdir}/staging/* ${ddir}/mfgtool/Profiles/Linux/OS\ Firmware/files/

	# copy qt5 image to files directory in mfgtool tree
	cp ${DEPLOY_DIR_IMAGE}/${platform}-qt5-image-imx6dl-slimline.tar.gz ${ddir}/mfgtool/Profiles/Linux/OS\ Firmware/files/

	# and finally zip it all up
	(cd ${DEPLOY_DIR_MFG}; zip -r ${mfgimagename}.zip ${mfgimagename})

	# cleanup
	rm -rf ${tmpdir}
}

do_cleanall() {
	rm -rf ${DEPLOY_DIR_MFG}
}
