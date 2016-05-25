# Copyright (C) 2014 Vivint, Inc.

DESCRIPTION = "Packages needed for network debug"
LICENSE = "MIT"

PR = "1"

inherit packagegroup

RDEPENDS_${PN} = " \
	iftop \
	iperf \
	iputils-ping \
	libpcap \
	netcat \
	tcpdump \
"
