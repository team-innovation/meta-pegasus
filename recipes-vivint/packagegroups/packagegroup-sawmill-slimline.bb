# Copyright (C) 2016 Vivint, Inc.
DESCRIPTION = "packages needed just for slimline"
LICENSE = "MIT"

PR = "r9"

inherit packagegroup

RDEPENDS_${PN} += " \
	u-boot-slimline \
	slimline-modules \
	hostapd \
	crda \
	psoc-fw-slimline \
	slimline-utils \
	slimline-version \
	mosquitto-clients \
        openvpn \
        telegraf \
"

