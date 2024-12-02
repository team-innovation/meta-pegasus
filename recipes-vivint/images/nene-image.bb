# Copyright (C) 2015 Freescale Semiconductor
# Copyright 2017-2019 NXP
# Released under the MIT license (see COPYING.MIT for the terms)
DESCRIPTION = "Image for Vivint next gen hub" 
LICENSE = "MIT"

inherit core-image

IMAGE_FEATURES += " \
    package-management \
    hwcodecs \
    ssh-server-openssh \
"

CORE_IMAGE_EXTRA_INSTALL += " \
    packagegroup-core-full-cmdline \
    packagegroup-tools-bluetooth \
    packagegroup-fsl-tools-audio \
    packagegroup-imx-security \
    packagegroup-fsl-gstreamer1.0 \
    packagegroup-fsl-gstreamer1.0-full \
"

IMAGE_INSTALL += " \
    libubootenv-bin \
    u-boot-imx-env \
    kernel-image \
    kernel-devicetree \
    imx-boot \
    mmc-utils \
    i2c-tools \
    udev-rules-vivint \
    evtest \
    linux-firmware-mediatek \
"
