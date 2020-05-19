# Copyright (C) 2013 Freescale Semiconductor

DESCRIPTION = "Bootloader for Vivint Brazen panel"

UBOOTBIN = "u-boot-brazen.imx"

# we don't use MACHINE here because wallsly, brazen and slimline u-boot images
# are both deployed
ALT_MACHINE = "imx6dl-brazen"
UBOOT_IMAGE = "u-boot-${ALT_MACHINE}-${PV}-${PR}.${UBOOT_SUFFIX}"
UBOOT_BINARY = "u-boot-brazen.${UBOOT_SUFFIX}"
UBOOT_SYMLINK = "u-boot-${ALT_MACHINE}.${UBOOT_SUFFIX}"
UBOOT_MACHINE = "mx6dlwallsly_config"

require recipes-bsp/u-boot/u-boot.inc

PROVIDES = "${PN}"

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=1707d6db1d42237583f50183a5651ecb"
GIT_BRAZEN_UBOOT_SERVER ?= "${GIT_SERVER}"
GIT_BRAZEN_UBOOT_BRANCH ?= "feature/brazen"
GIT_BRAZEN_UBOOT_REV ?= "${AUTOREV}"
GIT_BRAZEN_UBOOT_PROTOCOL ?= "ssh"

SRC_URI = "git://${GIT_BRAZEN_UBOOT_SERVER}/uboot-imx-brazen;protocol=${GIT_BRAZEN_UBOOT_PROTOCOL};branch=${GIT_BRAZEN_UBOOT_BRANCH} \
	   file://fix-build-for-gcc7.patch \
	   "

inherit fsl-u-boot-localversion

S = "${WORKDIR}/git"
LOCALVERSION ?= "-${SRCBRANCH}"

PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE = "(mx6)"

SRCREV = "${GIT_BRAZEN_UBOOT_REV}"
PV = "2017.03+git${SRCPV}"
PR = "r1"

do_compile_append() {
    mv ${B}/u-boot.${UBOOT_SUFFIX} ${B}/${UBOOT_BINARY}
}
