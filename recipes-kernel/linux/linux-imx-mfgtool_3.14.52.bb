# Copyright (C) 2014 O.S. Systems Software LTDA.
# Copyright (C) 2014, 2015 Freescale Semiconductor

SUMMARY = "Produces a Manufacturing Tool compatible Linux Kernel"
DESCRIPTION = "Linux Kernel provided and supported by Freescale that produces a \
Manufacturing Tool compatible Linux Kernel to be used in updater environment"

require recipes-kernel/linux/linux-imx.inc
require recipes-kernel/linux/linux-dtb.inc
require recipes-kernel/linux/linux-mfgtool.inc

DEPENDS += "lzop-native bc-native"

SRCBRANCH = "slimline-dizzy-3.14.52"
LOCALVERSION = "-1.0.0_slimline"
SRCREV = "${AUTOREV}"
KERNEL_SRC = "git://git.vivint.com/linux-imx.git;protocol=git"
SRC_URI = "${KERNEL_SRC};branch=${SRCBRANCH}"
PV = "3.14.52+git${SRCPV}"

PR = "r01"

DEFAULT_PREFERENCE = "1"

do_configure_prepend () {
    cp ${S}/arch/arm/configs/slimline_mfg_defconfig ${S}/.config
    cp ${S}/arch/arm/configs/slimline_mfg_defconfig ${S}/../defconfig
}

do_deploy () {
    install -d ${DEPLOY_DIR_IMAGE}
    install  arch/arm/boot/zImage ${DEPLOY_DIR_IMAGE}/zImage_mfgtool
}

COMPATIBLE_MACHINE = "(mx6|mx6ul|mx7)"
