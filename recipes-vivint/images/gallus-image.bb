# Copyright (C) 2015 Freescale Semiconductor
# Copyright 2017-2019 NXP
# Released under the MIT license (see COPYING.MIT for the terms)
DESCRIPTION = "Image for Vivint next gen hub" 
LICENSE = "MIT"

inherit core-image
inherit populate_sdk_qt5 populate_sdk_gallus

IMAGE_FEATURES += " \
    package-management \
    splash \
    hwcodecs \
    ${@bb.utils.contains('DISTRO_FEATURES', 'wayland', '', \
       bb.utils.contains('DISTRO_FEATURES',     'x11', 'x11-base x11-sato', \
                                                       '', d), d)} \
"

CONFLICT_DISTRO_FEATURES = "directfb"

IMAGE_INSTALL += " \
    packagegroup-qt5-imx \
    packagegroup-sawmill-common \
    packagegroup-sawmill-debugtools \
    packagegroup-sawmill-min \
    packagegroup-sawmill-sly \
    packagegroup-sawmill-video \
    packagegroup-fsl-gstreamer1.0 \
    packagegroup-fsl-gstreamer1.0-full \
    ${@bb.utils.contains('DISTRO_FEATURES', 'wayland', 'weston-init', '', d)} \
    packagegroup-core-full-cmdline \
    packagegroup-fsl-tools-gpu \
    packagegroup-tools-bluetooth \
    packagegroup-fsl-tools-audio \
"
