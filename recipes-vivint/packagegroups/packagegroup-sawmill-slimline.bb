# Copyright (C) 2016 Vivint, Inc.
DESCRIPTION = "packages needed just for slimline"
LICENSE = "MIT"

PR = "r5"

inherit packagegroup

RDEPENDS_${PN} += " \
	hostapd \
    crda \
	psoc-fw-slimline \
	slimline-utils \
	slimline-version \
"

