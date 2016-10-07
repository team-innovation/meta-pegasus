# Copyright (C) 2016 Vivint, Inc.
DESCRIPTION = "packages needed just for sly and future primary panels etc"
LICENSE = "MIT"

PR = "r5"

inherit packagegroup

RDEPENDS_${PN} += " \
	sly-version \
	psoc-fw-sly \
	psoc-fw-sly-fcc \
	touchlink-rtc-firmware \
	touchlink-telit-firmware-he910 \
	touchlink-telit-firmware-ue910 \
	fabric-go \
"

