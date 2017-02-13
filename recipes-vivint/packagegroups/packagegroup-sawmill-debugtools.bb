# Copyright (C) 2014 Vivint, Inc.

DESCRIPTION = "Packages needed for network debug"
LICENSE = "MIT"

PR = "3"

inherit packagegroup

RDEPENDS_${PN} = " \
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
