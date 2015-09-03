# Copyright (C) 2014 Vivint, Inc.

DESCRIPTION = "packages necessary for initial board setup"
LICENSE = "MIT"

PR = "1"

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
	util-linux-mkfs \
	util-linux-sfdisk \
	vivutils \
"
