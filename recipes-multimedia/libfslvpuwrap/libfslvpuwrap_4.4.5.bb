# Copyright (C) 2013-2016 Freescale Semiconductor
# Released under the MIT license (see COPYING.MIT for the terms)
DESCRIPTION = "Freescale Multimedia VPU wrapper"
DEPENDS = "imx-vpu"
LICENSE = "Proprietary"
SECTION = "multimedia"
LIC_FILES_CHKSUM = "file://COPYING;md5=80c0478f4339af024519b3723023fe28"

SRC_URI = "${FSL_MIRROR}/imx-vpuwrap-${PV}.bin;fsl-eula=true"
S = "${WORKDIR}/imx-vpuwrap-${PV}"

SRC_URI[md5sum] = "dc462fd44682013153144187c2e6b6b0"
SRC_URI[sha256sum] = "7ebb6b322a67fbbb902222f949ba09a7e52120b4102e1ad969e8b1491c42fa92"


inherit fsl-eula-unpack autotools pkgconfig

do_install_append() {
    # FIXME: Drop examples for now
    rm -r ${D}${datadir}
}

PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_MACHINE = "(mx6q|mx6dl)"
