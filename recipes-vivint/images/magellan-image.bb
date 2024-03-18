# Copyright (C) 2015 Freescale Semiconductor
# Copyright 2017-2019 NXP
# Released under the MIT license (see COPYING.MIT for the terms)
DESCRIPTION = "Image for Vivint next gen hub" 
LICENSE = "MIT"

inherit core-image
inherit uuu-zip-image

IMAGE_FEATURES += " \
    package-management \
    splash \
    hwcodecs \
    ${@bb.utils.contains('DISTRO_FEATURES', 'wayland', 'weston', \
       bb.utils.contains('DISTRO_FEATURES',     'x11', 'x11-base x11-sato', \
                                                       '', d), d)} \
"
IMAGE_INSTALL += " \
    packagegroup-core-full-cmdline-utils \
    packagegroup-tools-bluetooth \
    u-boot-scr \
    u-boot-imx \
    imx-boot \
    kernel-devicetree \
    evtest \
    weston-examples \
"
