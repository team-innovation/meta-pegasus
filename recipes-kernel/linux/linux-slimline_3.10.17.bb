# Adapted from linux-imx.inc, copyright (C) 2013 O.S. Systems Software LTDA
# Released under the MIT license (see COPYING.MIT for the terms)

require recipes-kernel/linux/linux-imx.inc
require recipes-kernel/linux/linux-dtb.inc

DESCRIPTION = "Linux kernel for Vivint Slimline panel"

SRC_URI = "git://git.vivint.com/linux-imx.git;branch=master \
	   file://defconfig \
"

LOCALVERSION = "-yocto"
SRCREV = "${AUTOREV}"


# GPU support patches
#SRC_URI += "file://drm-vivante-Add-00-sufix-in-returned-bus-Id.patch \
#"

COMPATIBLE_MACHINE = "(mx6)"
