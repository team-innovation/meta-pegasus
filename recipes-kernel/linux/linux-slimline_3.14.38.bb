# Copyright (C) 2013-2015 Freescale Semiconductor
# Released under the MIT license (see COPYING.MIT for the terms)

SUMMARY = "Linux Kernel for Vivint Slimline panel"
DESCRIPTION = "Linux Kernel based on original from Freescale modified \
with Vivint extensions and customizations."

require recipes-kernel/linux/linux-imx.inc
require recipes-kernel/linux/linux-dtb.inc

DEPENDS += "lzop-native bc-native"

SRCBRANCH = "slimline-next"
LOCALVERSION = "-slimline"
SRCREV = "${AUTOREV}"
KERNEL_SRC ?= "git://git.vivint.com/linux-imx.git;protocol=git"
SRC_URI = "${KERNEL_SRC};branch=${SRCBRANCH}"

DEFAULT_PREFERENCE = "1"

do_configure_prepend() {
   # copy latest defconfig for imx_v7_defoonfig to use
   cp ${S}/arch/arm/configs/slimline_defconfig ${B}/.config
   cp ${S}/arch/arm/configs/slimline_defconfig ${B}/../defconfig
}

COMPATIBLE_MACHINE = "(mx6)"
