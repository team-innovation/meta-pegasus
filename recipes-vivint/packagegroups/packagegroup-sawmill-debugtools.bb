# Copyright (C) 2014 Vivint, Inc.

DESCRIPTION = "Packages needed for network debug"
LICENSE = "MIT"

PR = "5"

inherit packagegroup

RDEPENDS_${PN} = " \
        python3-flent \
        netperf \
	gdb \
	perf \
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
"
