# Copyright (C) 2013 Freescale Semiconductor

DESCRIPTION = "bootloader for Vivint Slimline panel"
require recipes-bsp/u-boot/u-boot.inc

PROVIDES += "u-boot"

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=1707d6db1d42237583f50183a5651ecb"

SRC_URI = "git://git@${GIT_SERVER}/uboot-imx.git;protocol=ssh;branch=develop \
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

