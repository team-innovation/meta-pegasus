# Copyright (C) 2014 Vivint, Inc.

DESCRIPTION = "Packages needed for network debug"
LICENSE = "MIT"

PR = "4"

inherit packagegroup

RDEPENDS_${PN} = " \
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
	netcat \
	tcpdump \
"
