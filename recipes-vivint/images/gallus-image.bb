# Copyright (C) 2015 Freescale Semiconductor
# Copyright 2017-2019 NXP
# Released under the MIT license (see COPYING.MIT for the terms)

require recipes-fsl/images/imx-image-multimedia.bb

inherit populate_sdk_qt5

CONFLICT_DISTRO_FEATURES = "directfb"

IMAGE_INSTALL += " \
    packagegroup-qt5-imx \
    packagegroup-vivint \
"
