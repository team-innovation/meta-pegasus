
# openwrt build file
# Copyright (C) 2020, Vivint
#

HOMEPAGE = "www.vivint.com"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://../COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

SRC_URI_append = " file://COPYING"

MT7620_uImage = "MT7620_uImage"
MT7620_7612_uImage = "MT7620_MT7612_MESH_NODE_uImage"
MT7620_uboot = "MT7620_uboot.img"
REPO_DIR = "git/wallsly"


inherit update-rc.d

INITSCRIPT_NAME = "networkmodule"
INITSCRIPT_PARAMS = "stop 16 0 6 ."

PACKAGES = "${PN}-mt7620 \
	    "

do_configure[noexec] = "1"

do_compile[noexec] = "1"

do_install() {
    install -d ${D}${FW_DIR}

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


FILES_${PN}-mt7620 = "\
    ${FW_DIR}/MT7620_uboot.img \
    ${FW_DIR}/MT7620_uboot.img.md5sum \
    ${FW_DIR}/MT7620_uImage \
    ${FW_DIR}/MT7620_uImage.md5sum \
    ${FW_DIR}/MT7620_MT7612_MESH_NODE_uImage \
    ${FW_DIR}/MT7620_MT7612_MESH_NODE_uImage.md5sum \
    "
