# Copyright (C) 2014 Vivint, Inc.

DESCRIPTION = "Packages needed for network debug"
LICENSE = "MIT"

PR = "2"

inherit packagegroup

RDEPENDS_${PN} = " \
	dhrystone \
	i2c-tools \
	iftop \
	iperf \
	iputils-ping \
	libpcap \
	netcat \
	tcpdump \
"
