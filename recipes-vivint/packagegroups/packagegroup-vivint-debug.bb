DESCRIPTION = "Package group used by Vivint for debug tools" 
SUMMARY = "Vinint package group - debug tools"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

RDEPENDS:${PN} = " \
    iperf3 \
    nmap \
"
