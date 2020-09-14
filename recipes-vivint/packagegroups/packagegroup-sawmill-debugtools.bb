# Copyright (C) 2014 Vivint, Inc.

DESCRIPTION = "Packages needed for network debug"
LICENSE = "MIT"

inherit packagegroup

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
	iperf3 \
	iproute2-tc \
	iputils-ping \
	libpcap \
	lsof \
	netcat \
	tcpdump \
	${DEBUG_PACKAGES} \
"
