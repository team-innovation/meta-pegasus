DESCRIPTION = "Package group used by Vivint for debug tools" 
SUMMARY = "Vinint package group - debug tools"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

PANEL_DEBUG_PACKAGES = "gdb gdbserver valgrind"

RDEPENDS:${PN} = " \
    iperf3 \
    nmap \
    python3-flent \
    netperf \
    iproute2-tc \
    iproute2-ss \
    iproute2-nstat \
    iputils-ping \
    iputils-ping6 \
    iputils-tracepath \
    tcpdump \
    netcat \
    lsof \
    iftop \
    dhrystone \
    ${@bb.utils.contains('BUILD_TYPE', 'dbg','${DEBUG_PACKAGES}', '', d)} \
"
