# Copyright (C) 2015 Freescale Semiconductor
# Copyright 2017-2019 NXP
# Released under the MIT license (see COPYING.MIT for the terms)
DESCRIPTION = "Image for Vivint next gen hub" 
LICENSE = "MIT"

inherit core-image
inherit populate_sdk_qt5

IMAGE_FEATURES += " \
    package-management \
    splash \
    hwcodecs \
"

CONFLICT_DISTRO_FEATURES = "directfb"

IMAGE_INSTALL += " \
    packagegroup-qt5-imx \
    packagegroup-vivint \
"
