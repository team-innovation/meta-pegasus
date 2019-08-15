# Copyright (C) 2014-2018 O.S. Systems Software LTDA.
# Copyright (C) 2014-2016 Freescale Semiconductor

SUMMARY = "Produces a Manufacturing Tool compatible Linux Kernel"
DESCRIPTION = "Linux Kernel provided and supported by Freescale that produces a \
Manufacturing Tool compatible Linux Kernel to be used in updater environment"


GIT_KERNEL_BRANCH = "feature/4.14.98_2.0.0_ga"
GIT_KERNEL_SERVER = "${GIT_SERVER}"
GIT_KERNEL_PROTOCOL = "ssh"
GIT_KERNEL_REV = "${AUTOREV}"
SRCREV = "${GIT_KERNEL_REV}"
KERNEL_SRC = "git://${GIT_KERNEL_SERVER}/linux-imx;protocol=${GIT_KERNEL_PROTOCOL}"
SRC_URI = "${KERNEL_SRC};branch=${GIT_KERNEL_BRANCH}"

           
PR = "r23"
PV = "4.14.98+git${SRCPV}"

do_deploy_prepend() {
    install -d ${DEPLOY_DIR_IMAGE}
    install  arch/arm/boot/zImage ${DEPLOY_DIR_IMAGE}/zImage_mfgtool
}

require linux-imx_${PV}.bb
require linux-mfgtool.inc

KERNEL_IMAGE_BASE_NAME[vardepsexclude] = "DATETIME"
MODULE_IMAGE_BASE_NAME[vardepsexclude] = "DATETIME"
do_package[vardepsexclude] = "DATETIME"
