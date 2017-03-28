# openwrt build file
# Copyright (C) 2017, Vivint
#

DESCRIPTION = "OpenWrt RT3352/MT7620 Network Module"
HOMEPAGE = "www.vivint.com"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://../COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

PR = "r22"
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

SRC_URI[RT3352_uImage.md5sum] = "877491cc8b2d5c4168bc4b5c3b2b7054"
SRC_URI[RT3352_uImage.sha256sum] = "8001ec8f11044ee9cccb693821de5f0bf092cbedadbbc9ca4e460fa63c79d2d0"
SRC_URI[RT3352_uboot.md5sum] = "d78a51f5fdb52747df72bce5989b76aa"
SRC_URI[RT3352_uboot.sha256sum] = "bf32d459691fe2e95f6d376cd293df260d985781dc4535c4dbfdc38b9bfbc90b"
SRC_URI[MT7620_uImage.md5sum] = "7bd2d2a18971218a31213bf5fe2bf2c0"
SRC_URI[MT7620_uImage.sha256sum] = "f2a154e12a5ecb4f57a4b4d3095faf599090b7923879af3401837efd7148896c"
SRC_URI[MT7620_uboot.md5sum] = "2a0895c22467e353a8b007856240451e"
SRC_URI[MT7620_uboot.sha256sum] = "568391ba7a12c65987de834b02a474a879e4c18ad2a962f5813d1bb8f3464f4a"

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

