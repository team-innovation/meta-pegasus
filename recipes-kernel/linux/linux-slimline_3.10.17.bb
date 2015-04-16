# Adapted from linux-imx.inc, copyright (C) 2013 O.S. Systems Software LTDA
# Released under the MIT license (see COPYING.MIT for the terms)

require recipes-kernel/linux/linux-imx.inc
require recipes-kernel/linux/linux-dtb.inc

DESCRIPTION = "Linux kernel for Vivint Slimline panel"

SRC_URI = "\
	git://git.vivint.com/linux-imx.git;branch=master \
"

LOCALVERSION = "-yocto"
SRCREV = "${AUTOREV}"

# use the kernel defconfig instead of expecting on in the recipe dir
do_pre_configure() {
	cp ${S}/arch/arm/configs/slimline_defconfig ${WORKDIR}/defconfig
}

addtask pre_configure before do_configure after do_patch

# GPU support patches
#SRC_URI += "file://drm-vivante-Add-00-sufix-in-returned-bus-Id.patch \
#"

COMPATIBLE_MACHINE = "(mx6)"
