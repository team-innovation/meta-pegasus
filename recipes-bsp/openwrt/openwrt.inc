# openwrt build file
# Copyright (C) 2017, Vivint
#

DESCRIPTION = "OpenWrt RT3352/MT7620 Network Module"
HOMEPAGE = "www.vivint.com"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://../COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

PR = "r3"
SRCREV = "${AUTOREV}"
MODULE = "openwrt"

RPROVIDES_${PN} = "openwrt"

RREPLACES_${PN} = "bsp-rt288x-hg"
RCONFLICTS_${PN} = "bsp-rt288x-hg"

BB_STRICT_CHECKSUM = "0"

DOWNLOAD_SERVER="updateseng.vivint.com/innovation/openwrt/check"

SRC_URI = "http://${DOWNLOAD_SERVER}/RT3352_uImage \
           http://${DOWNLOAD_SERVER}/RT3352_uboot.img \
           http://${DOWNLOAD_SERVER}/MT7620_uImage \
           http://${DOWNLOAD_SERVER}/MT7620_uboot.img \
           file://COPYING \
           "
FW_DIR = "${base_libdir}/firmware"

inherit gettext

do_configure() {
    :
}

do_compile() {
    :
}

do_install() {
    install -d ${D}${FW_DIR}

    #RT3352
    install ${WORKDIR}/RT3352_uboot.img ${D}${FW_DIR}/RT3352_uboot.img
    install ${WORKDIR}/RT3352_uImage ${D}${FW_DIR}/RT3352_uImage

    md5sum ${D}${FW_DIR}/RT3352_uboot.img > ${D}${FW_DIR}/RT3352_uboot.img.md5sum
    md5sum ${D}${FW_DIR}/RT3352_uImage > ${D}${FW_DIR}/RT3352_uImage.md5sum
    
    #MT7620
    install ${WORKDIR}/MT7620_uboot.img ${D}${FW_DIR}/MT7620_uboot.img
    install ${WORKDIR}/MT7620_uImage ${D}${FW_DIR}/MT7620_uImage

    md5sum ${D}${FW_DIR}/MT7620_uboot.img > ${D}${FW_DIR}/MT7620_uboot.img.md5sum
    md5sum ${D}${FW_DIR}/MT7620_uImage > ${D}${FW_DIR}/MT7620_uImage.md5sum
}

FILES_${PN} = "\
    ${FW_DIR} \
    ${FW_DIR}\RT3352_uboot.img \
    ${FW_DIR}\RT3352_uboot.img.md5sum \
    ${FW_DIR}\RT3352_uImage \
    ${FW_DIR}\RT3352_uImage.md5sum \
    ${FW_DIR}\MT7620_uboot.img \
    ${FW_DIR}\MT7620_uboot.img.md5sum \
    ${FW_DIR}\MT7620_uImage \
    ${FW_DIR}\MT7620_uImage.md5sum \
    "

