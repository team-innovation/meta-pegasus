# Copyright (C) 2013 Freescale Semiconductor

DESCRIPTION = "Bootloader for Vivint WallSly panel"

UBOOTBIN = "u-boot-wallsly.imx"

# we don't use MACHINE here because wallsly and slimline u-boot images
# are both deployed
ALT_MACHINE = "imx6dl-wallsly"
UBOOT_IMAGE = "u-boot-${ALT_MACHINE}-${PV}-${PR}.${UBOOT_SUFFIX}"
UBOOT_BINARY = "u-boot-wallsly.${UBOOT_SUFFIX}"
UBOOT_SYMLINK = "u-boot-${ALT_MACHINE}.${UBOOT_SUFFIX}"
UBOOT_MACHINE = "mx6dlwallsly_config"

require recipes-bsp/u-boot/u-boot.inc

PROVIDES = "${PN}"

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=1707d6db1d42237583f50183a5651ecb"

GIT_UBOOT_SERVER ?= "${GIT_SERVER}"
GIT_WALLSLY_UBOOT_BRANCH ?= "release/wallsly-3.10.4"
SRC_URI = "git://${GIT_SERVER}/uboot-imx;protocol=ssh;branch=${GIT_WALLSLY_UBOOT_BRANCH}"

SRCREV = "${AUTOREV}"
PV = "2013.04+git${SRCPV}"
PR = "r15"

S = "${WORKDIR}/git"

PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE = "(mx6)"

inherit deploy

do_install () {
    install -d ${D}/boot
    install ${S}/u-boot.imx ${D}/boot/${UBOOT_IMAGE}
    ln -sf ${UBOOT_IMAGE} ${D}/boot/${UBOOT_BINARY}

    if [ "x${SPL_BINARY}" != "x" ]
    then
        install ${S}/${SPL_BINARY} ${D}/boot/${SPL_IMAGE}
        ln -sf ${SPL_IMAGE} ${D}/boot/${SPL_BINARY}
    fi
}

do_deploy () {
    install -d ${DEPLOYDIR}
    install ${S}/u-boot.imx \
            ${DEPLOYDIR}/${UBOOT_IMAGE}

    cd ${DEPLOYDIR}
    rm -f ${UBOOT_SYMLINK}
    ln -sf ${UBOOT_IMAGE} ${UBOOT_SYMLINK}
}

addtask deploy after do_install before do_build
