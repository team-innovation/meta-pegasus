# Copyright (C) 2014 Vivint, Inc.

DESCRIPTION = "packages necessary for initial board setup"
LICENSE = "MIT"

inherit packagegroup

RDEPENDS_${PN} = " \
	kernel-image kernel-devicetree \
	util-linux-sfdisk \
	util-linux-mkfs e2fsprogs-mke2fs \
	util-linux-agetty \
	slimline-initemmc \
	u-boot-fw-utils \
	u-boot-slimline"
