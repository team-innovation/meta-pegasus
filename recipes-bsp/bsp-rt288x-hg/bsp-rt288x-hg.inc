# bsp-rt288x-hg build file
# Copyright (C) 2016, Vivint
#

DESCRIPTION = "RT288x Network Module"
HOMEPAGE = "www.vivint.com"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://../COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

PR = "r1"
SRCREV = "${AUTOREV}"
MODULE = "bsp-rt288x-hg"


BB_STRICT_CHECKSUM = "0"

DOWNLOAD_SERVER="updateseng.vivint.com/innovation/rt3352/check"

SRC_URI = "http://${DOWNLOAD_SERVER}/RT3352_uImage \
           http://${DOWNLOAD_SERVER}/uboot.img \
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
    install ${WORKDIR}/uboot.img ${D}${FW_DIR}/RT3352_uboot.img
    install ${WORKDIR}/RT3352_uImage ${D}${FW_DIR} 

    md5sum ${D}${FW_DIR}/RT3352_uboot.img > ${D}${FW_DIR}/RT3352_uboot.img.md5sum
    md5sum ${D}${FW_DIR}/RT3352_uImage > ${D}${FW_DIR}/RT3352_uImage.md5sum
}

FILES_${PN} = "\
    ${FW_DIR} \
    ${FW_DIR}\RT3352_uboot.img \
    ${FW_DIR}\RT3352_uboot.img.md5sum \
    ${FW_DIR}\RT3352_uImage \
    ${FW_DIR}\RT3352_uImage.md5sum \
    "

