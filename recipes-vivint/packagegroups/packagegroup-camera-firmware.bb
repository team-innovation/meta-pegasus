# Copyright (C) 2016 Vivint, Inc.

DESCRIPTION = "packages for touchlink firmware"
LICENSE = "MIT"

PR = "r4"

inherit packagegroup

RDEPENDS_${PN} += " \
	touchlink-alpha-firmware-cs6022-ov4689 \
	touchlink-vivotek-firmware-721w \
	touchlink-vivotek-firmware-db8331w \
	touchlink-vivotek-firmware-db8332w \
	touchlink-vivotek-firmware-hd400w \
	touchlink-vivotek-vadp \
	touchlink-vivotek-vadp-rossini \
	touchlink-vivotek-vadp-rossini-crimson \
	touchlink-vivotek-vadp-rossini-sienna \
	touchlink-vivotek-vadp-rossini-matsuura \
"
