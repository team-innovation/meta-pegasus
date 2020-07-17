DESCRIPTION = "Awful hack to build zip of files for manufacturing"

inherit meta

LICENSE = "CLOSED"

DEPENDS += "rsync-native zip-native"
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
	f=$(latest '${platform}-mfg-image.*image.emmc.zip$' ${DEPLOY_DIR_IMAGE})
	cp ${f} ${ddir}/emmcimage
	cp ${f}.sha256 ${ddir}/emmcimage

	# copy mfgtool tree from recipe files sub-directory
	rsync -a -v ${rsyncexclude} ${FILE_DIRNAME}/files/mfgtool ${ddir}

	# get the rest of the files from the mfg rootfs tarball /boot directory
	#   u-boot
	#   nofec version of slimline dtb
	#   wallsly, brazen and hubplus dtb's
	#   normal and special reflash u-boot boot scripts
	#   
	# all the files we need are relative symlinks so cp with -L will convert
	# the links to files
	tar  xvf ${DEPLOY_DIR_IMAGE}/${platform}-mfg-image-imx6dl-slimline.tar.gz -C ${tmpdir} --wildcards ./boot/
	cp -L ${tmpdir}/boot/imx6dl-slimline-ldo-nofec.dtb  ${tmpdir}/staging/
	cp -L ${tmpdir}/boot/imx6dl-brazen*.dtb  ${tmpdir}/staging/ || true
	cp -L ${tmpdir}/boot/imx6dl-hubplus*.dtb  ${tmpdir}/staging/ || true
	cp -L ${tmpdir}/boot/imx6dl-wallsly*.dtb  ${tmpdir}/staging/ || true
	cp -L ${tmpdir}/boot/boot.scr ${tmpdir}/staging/
	cp -L ${tmpdir}/boot/usb-reflash-boot.scr ${tmpdir}/staging/
	cp -L ${tmpdir}/boot/binshell-boot.scr ${tmpdir}/staging/

	# copy from staging area to files directory in mfgtool tree
	cp ${tmpdir}/staging/* ${ddir}/mfgtool/Profiles/Linux/OS\ Firmware/files/

	# NOTE: copy prebuilt image 2013 uboot and 3.14 zImage so we can send file
	# There is a bug in the new version
	cp ${FILE_DIRNAME}/files/brazen-prebuilt/* ${ddir}/mfgtool/Profiles/Linux/OS\ Firmware/files/

	# copy mfg image to files directory in mfgtool tree
	cp ${DEPLOY_DIR_IMAGE}/${platform}-mfg-image-imx6dl-slimline.tar.gz ${ddir}/mfgtool/Profiles/Linux/OS\ Firmware/files/

	# Get the media/extra/factory_image directory into the tree
	#d=$(latest '${platform}-mfg-image.*media-extra.d$' ${DEPLOY_DIR_IMAGE})
	#tar -C ${d} -zcvf ${ddir}/mfgtool/Profiles/Linux/OS\ Firmware/files/media-extra.tar.gz factory_image 

	# and finally zip it all up
	(cd ${DEPLOY_DIR_MFG}; zip -r ${mfgimagename}.zip ${mfgimagename})

	# cleanup
	rm -rf ${tmpdir}
}

do_cleanall() {
	rm -rf ${DEPLOY_DIR_MFG}
}
