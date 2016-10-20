# Copyright (C) 2013-2015 Freescale Semiconductor
# Released under the MIT license (see COPYING.MIT for the terms)

SUMMARY = "Linux Kernel provided and supported by Freescale"
DESCRIPTION = "Linux Kernel provided and supported by Freescale with focus on \
i.MX Family Reference Boards. It includes support for many IPs such as GPU, VPU and IPU."

require recipes-kernel/linux/linux-imx.inc
require recipes-kernel/linux/linux-dtb.inc

DEPENDS += "lzop-native bc-native"

SRCBRANCH = "slimline-dizzy-3.14.52"
LOCALVERSION = "-1.0.0_slimline"
SRCREV = "${AUTOREV}"
KERNEL_SRC = "git://git.vivint.com/linux-imx.git;protocol=git"
SRC_URI = "${KERNEL_SRC};branch=${SRCBRANCH}"
PV = "3.14.52+git${SRCPV}"

PR = "r01"

DEFAULT_PREFERENCE = "1"

addtask copy_defconfig after do_unpack before do_configure
do_copy_defconfig () {
    cp ${S}/arch/arm/configs/slimline_defconfig ${S}/.config
    cp ${S}/arch/arm/configs/slimline_defconfig ${S}/../defconfig
}

KERNEL_MODULE_PROBECONF += "rtl8192ce snd-soc-cx20704 snd-soc-imx-cx20704 snd-soc-gsm030x snd-soc-imx-gsm030x"
KERNEL_MODULE_AUTOLOAD += "rtl8192ce atmel_mxt_ts psoc_swd viv_iod"
module_conf_rtl8192ce = "options rtl8192ce ips=0 fwlps=0 swlps=0 swenc=1"
module_conf_snd-soc-cx20704 = "blacklist snd-soc-cx20704"
module_conf_snd-soc-imx-cx20704 = "blacklist snd-soc-imx-cx20704"
module_conf_snd-soc-gsm030x = "blacklist snd-soc-gsm030x"
module_conf_snd-soc-imx-gsm030x = "blacklist snd-soc-imx-gsm030x"

#inherit fsl-vivante-kernel-driver-handler

COMPATIBLE_MACHINE = "(mx6|mx6ul|mx7)"
