
# openwrt build file
# Copyright (C) 2017, Vivint
#

DESCRIPTION = "OpenWrt RT3352/MT7620 Network Module"
HOMEPAGE = "www.vivint.com"
LICENSE = "GPLv2"

PR = "r70"
PV = "${SRCPV}"

RT3352_uImage = "RT3352_uImage"
RT3352_uboot = "RT3352_uboot.img"
MT7620_uImage = "MT7620_uImage"
MT7620_7612_uImage = "MT7620_MT7612_MESH_NODE_uImage"
MT7620_uboot = "MT7620_uboot.img"
REPO_DIR = "git/wallsly"

FW_DIR = "/lib/firmware"

do_configure[noexec] = "1"

do_compile[noexec] = "1"

do_install() {
    install -d ${D}${FW_DIR}

    install -d ${D}/${sysconfdir}/init.d
    install -m 0755 ${WORKDIR}/networkmodule ${D}/${sysconfdir}/init.d/

    #RT3352 u-boot
    install ${WORKDIR}/${REPO_DIR}/${RT3352_uboot} ${D}${FW_DIR}/RT3352_uboot.img
    md5sum ${D}${FW_DIR}/RT3352_uboot.img | awk '{ print $1 }' > ${D}${FW_DIR}/RT3352_uboot.img.md5sum

    #RT3352 sysrootfs
    install ${WORKDIR}/${REPO_DIR}/${RT3352_uImage} ${D}${FW_DIR}/RT3352_uImage
    md5sum ${D}${FW_DIR}/RT3352_uImage | awk '{ print $1 }' > ${D}${FW_DIR}/RT3352_uImage.md5sum
    
    #MT7620 u-boot
    install ${WORKDIR}/${REPO_DIR}/${MT7620_uboot} ${D}${FW_DIR}/MT7620_uboot.img
    md5sum ${D}${FW_DIR}/MT7620_uboot.img | awk '{ print $1 }' > ${D}${FW_DIR}/MT7620_uboot.img.md5sum

    #MT7620 sysrootfs
    install ${WORKDIR}/${REPO_DIR}/${MT7620_uImage} ${D}${FW_DIR}/MT7620_uImage
    md5sum ${D}${FW_DIR}/MT7620_uImage | awk '{ print $1 }' > ${D}${FW_DIR}/MT7620_uImage.md5sum

    #MT7620 sysrootfs for YoFi-Mesh
    install ${WORKDIR}/${REPO_DIR}/${MT7620_7612_uImage} ${D}${FW_DIR}/MT7620_MT7612_MESH_NODE_uImage
    md5sum ${D}${FW_DIR}/MT7620_MT7612_MESH_NODE_uImage | awk '{ print $1 }' > ${D}${FW_DIR}/MT7620_MT7612_MESH_NODE_uImage.md5sum
}

FILES_${PN}-rt3352 = "\
    ${FW_DIR}/RT3352_uboot.img \
    ${FW_DIR}/RT3352_uboot.img.md5sum \
    ${FW_DIR}/RT3352_uImage \
    ${FW_DIR}/RT3352_uImage.md5sum \
    "
FILES_${PN}-mt7620 = "\
    ${sysconfdir}/init.d/networkmodule \
    ${FW_DIR}/MT7620_uboot.img \
    ${FW_DIR}/MT7620_uboot.img.md5sum \
    ${FW_DIR}/MT7620_uImage \
    ${FW_DIR}/MT7620_uImage.md5sum \
    ${FW_DIR}/MT7620_MT7612_MESH_NODE_uImage \
    ${FW_DIR}/MT7620_MT7612_MESH_NODE_uImage.md5sum \
    "
