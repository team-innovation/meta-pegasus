DEPENDS += "uuu-tool"

do_configure[depends] += "fsl-image-mfgtool-initramfs:do_image_complete"

python do_uuu_zip_image() {

    # hardcoded
    TARGET_FSTYPE = "wic.bz2"
    INITRAMFS_PRE = "fsl-image-mfgtool-initramfs"
    uuu_auto = "uuu.auto"
    uuu_serial = "save-restore-serialnums.sh"
    uuu_path = "uuu"

    # deploy directory (e.g., deploy/images/imx8mm-gallus/*)
    ddi = d.getVar('DEPLOY_DIR_IMAGE')

    # Files in the target zip archive:
    #     - uuu scripts
    #         * uuu.auto - instructions for uuu - how to boot and flash
    #     - boot to ram (helpers):
    #         * uboot
    #         * devicetree
    #         * kernel
    #         * initramfs
    #     - util scripts
    #         * save-restore-serialnums.sh - on boot to ram - backup serial numbers
    #     - partitioned emmc media image for dd
    #         * image.wic.bz2 - written to emmc by dd

    uboot = d.getVar('IMAGE_BOOTLOADER')
    # ideally: if KERNEL_DEVICETREE is not a single one then pick one that matches UBOOT_DTB_NAME
    devicetree = d.getVar('UBOOT_DTB_NAME')
    kernel = d.getVar('KERNEL_IMAGETYPE')
    initramfs = INITRAMFS_PRE+"-"+d.getVar('MACHINE')+".cpio.gz.u-boot"

    # emmc image name
    wic_link_name = d.getVar('IMAGE_LINK_NAME')

    # basic check
    fstypes = d.getVar('IMAGE_FSTYPES').split()
    if not TARGET_FSTYPE in fstypes:
        bb.warn("Target image format ("+TARGET_FSTYPE+") is not built ("+str(fstypes)+").")
        return

    from zipfile import ZipFile
    import os.path

    wic_image = wic_link_name+"."+TARGET_FSTYPE

    # check if all files exist?
    files = [
        {'name': 'imx-boot',       'path': ddi+'/'+uboot},
        {'name': 'devicetree.dtb', 'path': ddi+'/'+devicetree},
        {'name': 'Image',          'path': ddi+'/'+kernel},
        {'name': 'initramfs.cpio.gz.u-boot',   'path': ddi+'/'+initramfs},
        {'name': os.path.basename(uuu_serial), 'path': ddi+'/'+uuu_serial},
        {'name': 'uuu.auto',       'path': ddi+'/'+uuu_auto},
        {'name': 'image.wic.bz2',  'path': ddi+'/'+wic_image}
    ]

    # create uuu dir? uuu-vivint should have already created it.
    if not os.path.exists(ddi+"/"+uuu_path):
        bb.warn("Target dir does not exist: "+ddi+"/"+uuu_path)
        return

    # simple name
    #zip_name = uuu_path+"/"+"vivint-"+d.getVar('MACHINE')+"-"+d.getVar('DATETIME')+".zip"
    zip_name = uuu_path+"/"+"vivint-"+d.getVar('MACHINE')+".zip"

    zipObj = ZipFile(ddi+'/'+zip_name, 'w')
    for f in files:
        zipObj.write(f['path'], f['name'])
    zipObj.close()

}

addtask uuu_zip_image after do_image_complete before do_populate_lic_deploy
