# Copyright (C) 2014-2018 O.S. Systems Software LTDA.
# Copyright (C) 2014-2016 Freescale Semiconductor

SUMMARY = "Produces a Manufacturing Tool compatible Linux Kernel"
DESCRIPTION = "Linux Kernel provided and supported by Freescale that produces a \
Manufacturing Tool compatible Linux Kernel to be used in updater environment"

PR = "r01"

require recipes-kernel/linux/linux-imx_${PV}.bb
require recipes-kernel/linux/linux-mfgtool.inc

SRCBRANCH = "kernel-4.14"
GIT_KERNEL_BRANCH = "vivint/kernel-4.14"
GIT_KERNEL_SERVER ?= "${GIT_SERVER}"
GIT_KERNEL_PROTOCOL ?= "ssh"
GIT_KERNEL_REV ?= "${AUTOREV}"
SRCREV = "${GIT_KERNEL_REV}"

KERNEL_SRC = "git://${GIT_KERNEL_SERVER}/linux-imx;protocol=${GIT_KERNEL_PROTOCOL}"
SRC_URI = "${KERNEL_SRC};branch=${GIT_KERNEL_BRANCH}"

DEFAULT_PREFERENCE = "1"

do_configure_prepend() {
   # copy latest defconfig slimline_defconfig to use
   cp ${S}/arch/arm/configs/slimline_mfg_defconfig ${S}/.config
   cp ${S}/arch/arm/configs/slimline_mfg_defconfig ${S}/../defconfig
   # HACK! overwrite imx_v7_defconfig so the above does not get
   # undone by this same task in linux-imx.bb
   cp ${S}/arch/arm/configs/slimline_mfg_defconfig ${S}/arch/arm/configs/imx_v7_mfg_defconfig
}

do_compile_prepend() {
   # Clean kernel 
   cd ${STAGING_KERNEL_DIR}
   oe_runmake mrproper
   cd -

}
# copy zImage to deploy directory
# update uImage with defconfig ane git info in name
# this is since build script can build multiple ways
# and will overwrite previous builds

do_deploy_append () {
    install -d ${DEPLOY_DIR_IMAGE}
    install  arch/arm/boot/zImage ${DEPLOY_DIR_IMAGE}/zImage_mfgtool
}

KERNEL_IMAGE_BASE_NAME[vardepsexclude] = "DATETIME"
MODULE_IMAGE_BASE_NAME[vardepsexclude] = "DATETIME"
do_package[vardepsexclude] = "DATETIME"
