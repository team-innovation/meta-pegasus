# Copyright (C) 2016 Vivint, Inc.
DESCRIPTION = "packages needed just for sly and future primary panels etc"
LICENSE = "MIT"

PR = "r9"

inherit packagegroup

RDEPENDS_${PN} += " \
	acm-usb-v6 \
	fabric-go \
	openvpn \
	psoc-fw-sly \
	psoc-fw-sly-fcc \
	sly-utils \
	sly-version \
	swdltool \
	touchlink-kwikset-firmware \
	touchlink-rtc-firmware \
	touchlink-telit-firmware-he910 \
	touchlink-telit-firmware-ue910 \
"

