
# openwrt build file
# Copyright (C) 2017, Vivint
#

DESCRIPTION = "OpenWrt RT3352/MT7620 Network Module"
HOMEPAGE = "www.vivint.com"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://../COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

PR = "r66"
SRCREV = "${AUTOREV}"
MODULE = "openwrt"

RPROVIDES_${PN} = "openwrt"

RREPLACES_${PN} = "bsp-rt288x-hg"
RCONFLICTS_${PN} = "bsp-rt288x-hg"

OPENWRT_BRANCH ?= "check"

DOWNLOAD_SERVER="updateseng.vivint.com/innovation/openwrt/${OPENWRT_BRANCH}"
LEDE_DOWNLOAD_SERVER="updateseng.vivint.com/innovation/lede/${OPENWRT_BRANCH}"

RT3352_uImage = "RT3352_uImage_201709111051"
RT3352_uboot = "RT3352_uboot.img_201709111051"
MT7620_uImage = "MT7620_uImage_201710041513"
MT7620_uboot = "MT7620_uboot.img_201710041513"

SRC_URI = "http://${DOWNLOAD_SERVER}/${RT3352_uImage};name=RT3352_uImage \
           http://${DOWNLOAD_SERVER}/${RT3352_uboot};name=RT3352_uboot \
           http://${LEDE_DOWNLOAD_SERVER}/${MT7620_uImage};name=MT7620_uImage \
           http://${LEDE_DOWNLOAD_SERVER}/${MT7620_uboot};name=MT7620_uboot \
           file://COPYING \
           "

SRC_URI[RT3352_uImage.md5sum] = "54fc25aec29b6b1c7fb93c897c282c0e"
SRC_URI[RT3352_uImage.sha256sum] = "d18cd5f179fb4d105d999c3c88d348a001b340e23968d368423b18e94c46e94c"
SRC_URI[RT3352_uboot.md5sum] = "d78a51f5fdb52747df72bce5989b76aa"
SRC_URI[RT3352_uboot.sha256sum] = "bf32d459691fe2e95f6d376cd293df260d985781dc4535c4dbfdc38b9bfbc90b"

SRC_URI[MT7620_uImage.md5sum] = "67df00bd24ecd1471389ca93598d50c2"
SRC_URI[MT7620_uImage.sha256sum] = "a3f4b1e4aa7350e1c2b0bc2f65188d21062598151685c79cef362336b61a45d9"
SRC_URI[MT7620_uboot.md5sum] = "a1b5c2cd225623553f021be5d0f16edf"
SRC_URI[MT7620_uboot.sha256sum] = "264b7673036d2b61c34bc4d62d5203f88af37dfc37143a9b7602058f5bcd8c6c"

FW_DIR = "${base_libdir}/firmware"

inherit gettext

addtask removedownloadedimages before do_fetch

do_removedownloadedimages() {
    rm -f ${DL_DIR}/RT3352*
    rm -f ${DL_DIR}/MT7620*
}

do_configure[noexec] = "1"

do_compile[noexec] = "1"

do_install() {
    install -d ${D}${FW_DIR}

    #RT3352 u-boot
    install ${WORKDIR}/${RT3352_uboot} ${D}${FW_DIR}/RT3352_uboot.img
    md5sum ${D}${FW_DIR}/RT3352_uboot.img | awk '{ print $1 }' > ${D}${FW_DIR}/RT3352_uboot.img.md5sum

    #RT3352 sysrootfs
    install ${WORKDIR}/${RT3352_uImage} ${D}${FW_DIR}/RT3352_uImage
    md5sum ${D}${FW_DIR}/RT3352_uImage | awk '{ print $1 }' > ${D}${FW_DIR}/RT3352_uImage.md5sum
    
    #MT7620 u-boot
    install ${WORKDIR}/${MT7620_uboot} ${D}${FW_DIR}/MT7620_uboot.img
    md5sum ${D}${FW_DIR}/MT7620_uboot.img | awk '{ print $1 }' > ${D}${FW_DIR}/MT7620_uboot.img.md5sum

    #MT7620 sysrootfs
    install ${WORKDIR}/${MT7620_uImage} ${D}${FW_DIR}/MT7620_uImage
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
