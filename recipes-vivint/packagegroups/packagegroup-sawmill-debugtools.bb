# Copyright (C) 2014 Vivint, Inc.

DESCRIPTION = "Packages needed for network debug"
LICENSE = "MIT"

PR = "6"

inherit packagegroup

PANEL_DEBUG_PACKAGES = "gdb"
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
