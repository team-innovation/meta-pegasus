FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://template_csf_fit.txt \
            file://template_csf_spl.txt \
            file://template_csf_image.txt \
            file://template_genIVT.pl \
	   "
DEPENDS += " \
	virtual/kernel \
	"

do_compile() {
	# Get the templates ready
	cp ${WORKDIR}/template_csf_fit.txt csf_fit.txt
	cp ${WORKDIR}/template_csf_spl.txt csf_spl.txt
	cp ${WORKDIR}/template_csf_image.txt csf_image.txt
	cp ${WORKDIR}/template_genIVT.pl genIVT.pl

	cp ${DEPLOY_DIR_IMAGE}/Image Image
	cp ${DEPLOY_DIR_IMAGE}/${BOOT_TOOLS}/u-boot.cfg u-boot.cfg

	compile_${SOC_FAMILY}
	# Copy TEE binary to SoC target folder to mkimage
	if ${DEPLOY_OPTEE}; then
		cp ${DEPLOY_DIR_IMAGE}/tee.bin ${BOOT_STAGING}
	fi
	# mkimage for i.MX8
	for target in ${IMXBOOT_TARGETS}; do
		if [ "$target" = "flash_linux_m4_no_v2x" ]; then
			# Special target build for i.MX 8DXL with V2X off
			bbnote "building ${SOC_TARGET} - ${REV_OPTION} V2X=NO ${target}"
			make SOC=${SOC_TARGET} ${REV_OPTION} V2X=NO  flash_linux_m4
		else
			bbnote "building ${SOC_TARGET} - ${REV_OPTION} ${target}"
			make SOC=${SOC_TARGET} ${REV_OPTION} ${target} 2>&1 | tee flash.log
		fi
		if [ -e "${BOOT_STAGING}/flash.bin" ]; then
			cp ${BOOT_STAGING}/flash.bin ${S}/${BOOT_CONFIG_MACHINE}-${target}
		fi
	done

	make SOC=${SOC_TARGET} print_fit_hab | tee fit.log

	# update SPL values
	SPL_START_ADDR=`awk '/spl hab block/{print $4}' flash.log`
	SPL_OFFSET=`awk '/spl hab block/{print $5}' flash.log`
	SPL_LENGTH=`awk '/spl hab block/{print $6}' flash.log`
	sed -i "s|_SPL_START_ADDR_|$SPL_START_ADDR|g" csf_spl.txt
	sed -i "s|_SPL_OFFSET_|$SPL_OFFSET|g" csf_spl.txt
	sed -i "s|_SPL_LENGTH_|$SPL_LENGTH|g" csf_spl.txt

	# update FIT values
	FIT_START_ADDR=`awk '/sld hab block/{print $4}' flash.log`
	FIT_OFFSET=`awk '/sld hab block/{print $5}' flash.log`
	FIT_LENGTH=`awk '/sld hab block/{print $6}' flash.log`
	sed -i "s|_FIT_START_ADDR_|$FIT_START_ADDR|g" csf_fit.txt
	sed -i "s|_FIT_OFFSET_|$FIT_OFFSET|g" csf_fit.txt
	sed -i "s|_FIT_LENGTH_|$FIT_LENGTH|g" csf_fit.txt
	UBOOT_START_ADDR=`awk 'NR==8{print $1}' fit.log`
	UBOOT_OFFSET=`awk 'NR==8{print $2}' fit.log`
	UBOOT_LENGTH=`awk 'NR==8{print $3}' fit.log`
	sed -i "s|_UBOOT_START_ADDR_|$UBOOT_START_ADDR|g" csf_fit.txt 
	sed -i "s|_UBOOT_OFFSET_|$UBOOT_OFFSET|g" csf_fit.txt
	sed -i "s|_UBOOT_LENGTH_|$UBOOT_LENGTH|g" csf_fit.txt
	DTB_START_ADDR=`awk 'NR==9{print $1}' fit.log`
	DTB_OFFSET=`awk 'NR==9{print $2}' fit.log`
	DTB_LENGTH=`awk 'NR==9{print $3}' fit.log` 
	sed -i "s|_DTB_START_ADDR_|$DTB_START_ADDR|g" csf_fit.txt
	sed -i "s|_DTB_OFFSET_|$DTB_OFFSET|g" csf_fit.txt
	sed -i "s|_DTB_LENGTH_|$DTB_LENGTH|g" csf_fit.txt
	ATF_START_ADDR=`awk 'NR==10{print $1}' fit.log`
	ATF_OFFSET=`awk 'NR==10{print $2}' fit.log`
	ATF_LENGTH=`awk 'NR==10{print $3}' fit.log`
	sed -i "s|_ATF_START_ADDR_|$ATF_START_ADDR|g" csf_fit.txt
	sed -i "s|_ATF_OFFSET_|$ATF_OFFSET|g" csf_fit.txt
	sed -i "s|_ATF_LENGTH_|$ATF_LENGTH|g" csf_fit.txt
	TEE_START_ADDR=`awk 'NR==11{print $1}' fit.log`
	TEE_OFFSET=`awk 'NR==11{print $2}' fit.log`
	TEE_LENGTH=`awk 'NR==11{print $3}' fit.log`
	sed -i "s|_TEE_START_ADDR_|$TEE_START_ADDR|g" csf_fit.txt
	sed -i "s|_TEE_OFFSET_|$TEE_OFFSET|g" csf_fit.txt
	sed -i "s|_TEE_LENGTH_|$TEE_LENGTH|g" csf_fit.txt

	#CSF_SPL_OFFSET=`awk '/csf_off/{print $2}' flash.log | head -n 1`
	#CSF_FIT_OFFSET=`awk '/csf_off/{print $2}' flash.log | tail -n 1`
	#dd if=csf_spl.bin of=${S}/${BOOT_CONFIG_MACHINE}-${target} seek=$(($CSF_SPL_OFFSET)) bs=1 conv=notrunc
	#dd if=csf_fit.bin of=${S}/${BOOT_CONFIG_MACHINE}-${target} seek=$(($CSF_FIT_OFFSET)) bs=1 conv=notrunc

	# Sign the kernel image
	LOADADDR=`awk '/define CONFIG_LOADADDR/{print $3}' u-boot.cfg`
	SIZE="0x`od -t x4 -j 0x10 -N 0x4 --endian=little Image | head -n1 | awk '{print $2}'`"
	IVTOFFSET=$SIZE
	IVTSIZE="0x20"
	CSFOFFSET=0x$(awk "BEGIN {printf \"%x\", $IVTOFFSET + $IVTSIZE}")	
	objcopy -I binary -O binary --pad-to=$SIZE --gap-fill=0x00 Image Image-pad.bin
	IVTADDRESS=0x$(awk "BEGIN {printf \"%x\", $LOADADDR + $IVTOFFSET}")
	CSFADDRESS=0x$(awk "BEGIN {printf \"%x\", $LOADADDR + $CSFOFFSET}")
	sed -i "s|_LOADADDR_|$LOADADDR|g" genIVT.pl
	sed -i "s|_IVTADDRESS_|$IVTADDRESS|g" genIVT.pl
	sed -i "s|_CSFADDRESS_|$CSFADDRESS|g" genIVT.pl
	./genIVT.pl
	cat Image-pad.bin ivt.bin > Image-pad-ivt.bin
	sed -i "s|_IMAGE_START_ADDR_|$LOADADDR|g" csf_image.txt
	sed -i "s|_IMAGE_OFFSET_|0x0|g" csf_image.txt
	sed -i "s|_IMAGE_LENGTH_|$CSFOFFSET|g" csf_image.txt

	#cat Image-pad-ivt.bin csf_image.bin > Image-signed.bin
	#rm Image-pad*
}
