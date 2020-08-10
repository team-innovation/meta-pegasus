# openwrt build file
# Copyright (C) 2017, Vivint
#

require openwrt.inc

GIT_ARTIFACTS_BRANCH ?= "develop"
GIT_ARTIFACTS_SERVER ?= "${GIT_SERVER}"
GIT_ARTIFACTS_PROTOCOL ?= "ssh"

GIT_ARTIFACTS_REV ?= "${AUTOREV}"
SRCREV = "${GIT_ARTIFACTS_REV}"

RT3352_uImage = "RT3352_uImage"
RT3352_uboot = "RT3352_uboot.img"

SRC_URI_append = "git://${GIT_ARTIFACTS_SERVER}/artifacts;protocol=${GIT_ARTIFACTS_PROTOCOL};branch=${GIT_ARTIFACTS_BRANCH} \
		  file://networkmodule"

FW_DIR = "/lib/firmware"

PACKAGES += "${PN}-rt3352 \
             ${PN}-mt7620 \
	    "
do_install_append() {

    install -d ${D}/${sysconfdir}/init.d
    install -m 0755 ${WORKDIR}/networkmodule ${D}/${sysconfdir}/init.d/

    #RT3352 u-boot
    install ${WORKDIR}/${REPO_DIR}/${RT3352_uboot} ${D}${FW_DIR}/RT3352_uboot.img
    md5sum ${D}${FW_DIR}/RT3352_uboot.img | awk '{ print $1 }' > ${D}${FW_DIR}/RT3352_uboot.img.md5sum

    #RT3352 sysrootfs
    install ${WORKDIR}/${REPO_DIR}/${RT3352_uImage} ${D}${FW_DIR}/RT3352_uImage
    md5sum ${D}${FW_DIR}/RT3352_uImage | awk '{ print $1 }' > ${D}${FW_DIR}/RT3352_uImage.md5sum
}

FILES_${PN}-rt3352 = "\
    ${FW_DIR}/RT3352_uboot.img \
    ${FW_DIR}/RT3352_uboot.img.md5sum \
    ${FW_DIR}/RT3352_uImage \
    ${FW_DIR}/RT3352_uImage.md5sum \
    "

FILES_${PN}-mt7620 += "\
    ${sysconfdir}/init.d/networkmodule \
    "
