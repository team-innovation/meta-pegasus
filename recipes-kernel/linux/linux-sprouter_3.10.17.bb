# Adapted from linux-imx.inc, copyright (C) 2013 O.S. Systems Software LTDA
# Released under the MIT license (see COPYING.MIT for the terms)

require recipes-kernel/linux/linux-imx.inc
require recipes-kernel/linux/linux-dtb.inc

DESCRIPTION = "Linux kernel for Vivint Space Monkey Router panel"

SRC_URI = "git://git.vivint.com/linux-imx.git;branch=master \
	   file://defconfig \
"

LOCALVERSION = "-yocto"
SRCREV = "${AUTOREV}"

COMPATIBLE_MACHINE = "(mx6)"
