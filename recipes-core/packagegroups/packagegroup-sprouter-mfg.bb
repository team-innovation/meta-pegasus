# Copyright (C) 2015 Vivint, Inc. - Space Monkey Router

DESCRIPTION = "packages necessary for initial board setup"
LICENSE = "MIT"

inherit packagegroup

RDEPENDS_${PN} = " \
	kernel-devicetree \
	util-linux-sfdisk \
	util-linux-mkfs e2fsprogs-mke2fs \
	sprouter-initemmc \
	u-boot-fw-utils \
	u-boot-sprouter \
	packagegroup-core-ssh-openssh \
	kernel-image \
	kernel-devicetree \
"
