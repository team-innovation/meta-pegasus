# Copyright (C) 2013 Freescale Semiconductor

DESCRIPTION = "bootloader for Vivint Slimline panel"
require recipes-bsp/u-boot/u-boot.inc

PROVIDES += "u-boot"

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=1707d6db1d42237583f50183a5651ecb"


GIT_UBOOT_SERVER ?= "${GIT_SERVER}"
GIT_UBOOT_BRANCH ?= "release/3.10.6"
GIT_UBOOT_PROTOCOL ?= "ssh"

SRC_URI = "git://${GIT_UBOOT_SERVER}/uboot-imx;protocol=${GIT_UBOOT_PROTOCOL};branch=${GIT_UBOOT_BRANCH} \
	   file://fw_env.config \
"

SRCREV = "${AUTOREV}"
PV = "2013.04+git${SRCPV}"
PR = "r15"

S = "${WORKDIR}/git"

PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE = "(mx6)"

UBOOTBIN = "u-boot.imx"

inherit deploy

do_deploy () {
    install -d ${DEPLOYDIR}
    install ${S}/${UBOOTBIN} \
            ${DEPLOYDIR}/${UBOOT_IMAGE}

    cd ${DEPLOYDIR}
    rm -f ${UBOOT_SYMLINK}
    ln -sf ${UBOOT_IMAGE} ${UBOOT_SYMLINK}
}

addtask deploy after do_install before do_build

