# Copyright (C) 2014 Vivint, Inc.

DESCRIPTION = "Minimal list of packages for a usable image \
	used for flashstation reflasing and also included in full image"

LICENSE = "MIT"

PR = "2"

inherit packagegroup

RDEPENDS_${PN} = " \
	e2fsprogs-mke2fs \
	iw \
	kernel-devicetree \
	kernel-image \
	ntp \
	openssh-sftp-server \
	packagegroup-core-ssh-openssh \
	psoc5-verify \
	psoc-fw \
	slimline-initemmc \
	strace \
	u-boot-fw-utils \
	u-boot-slimline \
	u-boot-script-slimline \
	util-linux-mkfs \
	util-linux-sfdisk \
	vivutils \
	memtester \
"
