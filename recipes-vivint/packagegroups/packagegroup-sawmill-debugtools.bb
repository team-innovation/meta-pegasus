# Copyright (C) 2014 Vivint, Inc.

DESCRIPTION = "Packages needed for network debug"
LICENSE = "MIT"

PR = "8"

inherit packagegroup

VIVINT_DEBUG_PACKAGES = "touchlink-apps-pumpernickel-dbg"
PANEL_DEBUG_PACKAGES = "gdb gdbserver perf valgrind glibc-dbg libc6-dev"
PYTHON_DEBUG_PACKAGES = "python3-pip python3-pydevd"

DEBUG_PACKAGES += "${PANEL_DEBUG_PACKAGES} \
                   ${PYTHON_DEBUG_PACKAGES} \
		  "
RDEPENDS_${PN} += " \
        python3-flent \
        netperf \
        fping \
	dhrystone \
	i2c-tools \
	iftop \
	iperf \
	iproute2-tc \
	iputils-ping \
	libpcap \
	lsof \
	netcat \
	tcpdump \
	${@base_contains('BUILD_TYPE', 'dbg','${DEBUG_PACKAGES}', '', d)} \
"
