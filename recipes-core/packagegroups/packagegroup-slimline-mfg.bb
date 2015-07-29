# Copyright (C) 2014 Vivint, Inc.

DESCRIPTION = "packages necessary for initial board setup"
LICENSE = "MIT"

inherit packagegroup

RDEPENDS_${PN} = " \
	kernel-devicetree \
	util-linux-sfdisk \
	util-linux-mkfs e2fsprogs-mke2fs \
	slimline-initemmc \
	u-boot-fw-utils \
	u-boot-slimline \
	packagegroup-core-ssh-openssh \
	openssh-sftp-server \
	kernel-image \
	kernel-devicetree \
	psoc5-verify \
"
