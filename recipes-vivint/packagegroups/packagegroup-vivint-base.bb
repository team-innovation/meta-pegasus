DESCRIPTION = "Package group used by Vivint for base system features" 
SUMMARY = "Vinint package group - base system"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

RDEPENDS:${PN} = " \
    i2c-tools \
    imx-boot \
    kernel-image \
    kernel-devicetree \
    mmc-utils \
    libgpiod \
    libubootenv-bin \
    nmap \
    pulseaudio-misc \
    pulseaudio-server \
    socat \
    udev-rules-vivint \
    viv-usb-utils \
"
