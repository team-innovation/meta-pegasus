# openwrt build file
# Copyright (C) 2017, Vivint
#

DESCRIPTION = "OpenWrt RT3352/MT7620 Network Module"
HOMEPAGE = "www.vivint.com"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://../COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

PR = "r10"
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
SRC_URI[RT3352_uImage.md5sum] = "d2e48e0acddc82e36c78469698498a5a"
SRC_URI[RT3352_uImage.sha256sum] = "f0e7f4b8450c95a8eeab353bc3344858edcd14e9af0408f441968ff60746b284"
SRC_URI[RT3352_uboot.md5sum] = "d78a51f5fdb52747df72bce5989b76aa"
SRC_URI[RT3352_uboot.sha256sum] = "bf32d459691fe2e95f6d376cd293df260d985781dc4535c4dbfdc38b9bfbc90b"
SRC_URI[MT7620_uImage.md5sum] = "beb32f1f48a1da7ff758eb559e8f3a06"
SRC_URI[MT7620_uImage.sha256sum] = "c35beb8074f80e6e6b4b18fa90869170af2c64de63fa44fa99052b67f4a71442"
SRC_URI[MT7620_uboot.md5sum] = "c917a291ac16826d76c51be3d895a4d1"
SRC_URI[MT7620_uboot.sha256sum] = "be9e22a08e88900402db5d27673751dfc18f1767a7c4ad97c4ae4dabd70a6ef9"

FW_DIR = "${base_libdir}/firmware"

inherit gettext

addtask removedownloadedimages before do_fetch

do_removedownloadedimages() {
    rm -f ${DL_DIR}/RT3352*
    rm -f ${DL_DIR}/MT7620*
}

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

