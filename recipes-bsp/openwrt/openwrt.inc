# openwrt build file
# Copyright (C) 2017, Vivint
#

DESCRIPTION = "OpenWrt RT3352/MT7620 Network Module"
HOMEPAGE = "www.vivint.com"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://../COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

PR = "r7"
SRCREV = "${AUTOREV}"
MODULE = "openwrt"

RPROVIDES_${PN} = "openwrt"

RREPLACES_${PN} = "bsp-rt288x-hg"
RCONFLICTS_${PN} = "bsp-rt288x-hg"

DOWNLOAD_SERVER="updateseng.vivint.com/innovation/openwrt/check"

SRC_URI = "http://${DOWNLOAD_SERVER}/RT3352_uImage;name=RT3352_uImage \
           http://${DOWNLOAD_SERVER}/RT3352_uboot.img;name=RT3352_uboot \
           http://${DOWNLOAD_SERVER}/MT7620_uImage;name=MT7620_uImage \
           http://${DOWNLOAD_SERVER}/MT7620_uboot.img;name=MT7620_uboot \
           file://COPYING \
           "

BB_STRICT_CHECKSUM="0"

FW_DIR = "${base_libdir}/firmware"

inherit gettext

python do_removedownloadedimages() {
    try:
        os.unlink(os.path.join(d.getVar('BSPDIR'), "downloads/RT3352_uImage"))
    except:
        pass
    try:
        os.unlink(os.path.join(d.getVar('BSPDIR'), "downloads/RT3352_uboot.img"))
    except:
        pass
    try:
        os.unlink(os.path.join(d.getVar('BSPDIR'), "downloads/MT7620_uImage"))
    except:
        pass
    try:
        os.unlink(os.path.join(d.getVar('BSPDIR'), "downloads/MT7620_uboot.img"))
    except:
        pass
    try:
        os.unlink(os.path.join(d.getVar('BSPDIR'), "downloads/RT3352_uImage.done"))
    except:
        pass
    try:
        os.unlink(os.path.join(d.getVar('BSPDIR'), "downloads/RT3352_uboot.img.done"))
    except:
        pass
    try:
        os.unlink(os.path.join(d.getVar('BSPDIR'), "downloads/MT7620_uImage.done"))
    except:
        pass
    try:
        os.unlink(os.path.join(d.getVar('BSPDIR'), "downloads/MT7620_uboot.img.done"))
    except:
        pass
}

addtask removedownloadedimages before do_fetch

do_configure() {
    :
}

do_compile() {
    :
}

do_install() {
    install -d ${D}${FW_DIR}

    #RT3352 u-boot
    install ${WORKDIR}/RT3352_uboot.img ${D}${FW_DIR}/RT3352_uboot.img
    md5sum ${D}${FW_DIR}/RT3352_uboot.img | awk '{ print $1 }' > ${D}${FW_DIR}/RT3352_uboot.img.md5sum

    #RT3352 sysrootfs
    install ${WORKDIR}/RT3352_uImage ${D}${FW_DIR}/RT3352_uImage
    md5sum ${D}${FW_DIR}/RT3352_uImage | awk '{ print $1 }' > ${D}${FW_DIR}/RT3352_uImage.md5sum
    
    #MT7620 u-boot
    install ${WORKDIR}/MT7620_uboot.img ${D}${FW_DIR}/MT7620_uboot.img
    md5sum ${D}${FW_DIR}/MT7620_uboot.img | awk '{ print $1 }' > ${D}${FW_DIR}/MT7620_uboot.img.md5sum

    #MT7620 sysrootfs
    install ${WORKDIR}/MT7620_uImage ${D}${FW_DIR}/MT7620_uImage
    md5sum ${D}${FW_DIR}/MT7620_uImage | awk '{ print $1 }' > ${D}${FW_DIR}/MT7620_uImage.md5sum
}

FILES_${PN}-rt3352 = "\
    ${FW_DIR}/RT3352_uboot.img \
    ${FW_DIR}/RT3352_uboot.img.md5sum \
    ${FW_DIR}/RT3352_uImage \
    ${FW_DIR}/RT3352_uImage.md5sum \
    "
FILES_${PN}-mt7620 = "\
    ${FW_DIR}/MT7620_uboot.img \
    ${FW_DIR}/MT7620_uboot.img.md5sum \
    ${FW_DIR}/MT7620_uImage \
    ${FW_DIR}/MT7620_uImage.md5sum \
    "

