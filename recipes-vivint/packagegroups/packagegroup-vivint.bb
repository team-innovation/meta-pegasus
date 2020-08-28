SUMMARY = "Vivint packages"

inherit packagegroup

RDEPENDS_${PN} = " \
    vivintkeys \
    tzdata \
    usbinit \
    iproute2 \
    kernel-module-g-mass-storage \
    kmod \
    init-ifupdown \
"
