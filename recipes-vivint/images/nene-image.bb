# Copyright (C) 2015 Freescale Semiconductor
# Copyright 2017-2019 NXP
# Released under the MIT license (see COPYING.MIT for the terms)
DESCRIPTION = "Image for Vivint next gen hub" 
LICENSE = "MIT"

inherit core-image
inherit uuu-zip-image

#IMAGE_FEATURES += " \
#    package-management \
#    ${@bb.utils.contains('DISTRO_FEATURES', 'wayland', '', \
#       bb.utils.contains('DISTRO_FEATURES',     'x11', 'x11-base x11-sato', \
#                                                       '', d), d)} \
#"


# IMAGE_INSTALL += " \
#    packagegroup-sawmill-common \
#    packagegroup-sawmill-debugtools \
#    packagegroup-sawmill-min \
#    packagegroup-sawmill-sly \
#    ${@bb.utils.contains('DISTRO_FEATURES', 'wayland', 'weston-init', '', d)} \
#    packagegroup-core-full-cmdline \
#    packagegroup-tools-bluetooth \
#    packagegroup-fsl-tools-audio \
#"
