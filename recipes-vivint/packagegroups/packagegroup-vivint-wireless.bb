DESCRIPTION = "Package group used by Vivint for wireless utilities" 
SUMMARY = "Vinint package group - wireless system"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

RDEPENDS:${PN} = " \
    wpa-supplicant \
    hostapd \
    bridge-utils \
"
