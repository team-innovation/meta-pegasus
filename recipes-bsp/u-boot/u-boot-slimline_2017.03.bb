# Copyright (C) 2013 Freescale Semiconductor

DESCRIPTION = "bootloader for Vivint Slimline panel"
require recipes-bsp/u-boot/u-boot.inc
PROVIDES += "u-boot"

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://Licenses/gpl-2.0.txt;md5=b234ee4d69f5fce4486a80fdaf4a4263"

GIT_UBOOT_SERVER = "${GIT_SERVER}"
GIT_UBOOT_BRANCH = "slimline-imx"
GIT_UBOOT_PROTOCOL = "ssh"
GIT_UBOOT_REV = "${AUTOREV}"
UBOOT_MACHINE = "mx6dlslimline_defconfig"

SRC_URI = "git://${GIT_UBOOT_SERVER}/uboot-imx;protocol=${GIT_UBOOT_PROTOCOL};branch=${GIT_UBOOT_BRANCH}"

S = "${WORKDIR}/git"

SRCREV = "${GIT_UBOOT_REV}"
inherit fsl-u-boot-localversion

LOCALVERSION ?= "-${SRCBRANCH}"

PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE = "(mx6)"

PV = "2017.03+git${SRCPV}"
PR = "r18"

