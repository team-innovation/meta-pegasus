# Copyright (C) 2016 Vivint, Inc.
DESCRIPTION = "packages needed just for slimline"
LICENSE = "MIT"

PR = "r4"

inherit packagegroup

RDEPENDS_${PN} += " \
	hostapd \
	psoc-fw-slimline \
	slimline-utils \
	slimline-version \
"

