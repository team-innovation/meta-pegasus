# Adapted from linux-imx.inc, copyright (C) 2013, 2014 O.S. Systems Software LTDA
# Released under the MIT license (see COPYING.MIT for the terms)

require recipes-kernel/linux/linux-imx.inc

SUMMARY = "Linux kernel for Vivint boards"
GIT_KERNEL_BRANCH = "feature/4.14.98_2.0.0_ga"
GIT_KERNEL_SERVER = "${GIT_SERVER}"
GIT_KERNEL_PROTOCOL = "ssh"
GIT_KERNEL_REV = "${AUTOREV}"
SRCREV = "${GIT_KERNEL_REV}"
KERNEL_SRC = "git://${GIT_KERNEL_SERVER}/linux-imx;protocol=${GIT_KERNEL_PROTOCOL}"
SRC_URI = "${KERNEL_SRC};branch=${GIT_KERNEL_BRANCH}"

LOCALVERSION = "-2.0.0-ga+yocto"
DEPENDS += "lzop-native bc-native"
COMPATIBLE_MACHINE = "(mx6|mx7|mx8)"

KERNEL_DEFCONFIG = "slimline_defconfig"
