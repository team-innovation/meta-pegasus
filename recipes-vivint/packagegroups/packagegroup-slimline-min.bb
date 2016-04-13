# Copyright (C) 2014 Vivint, Inc.
SUMMARY = "Minimal list of packages for a usable image"
DESCRIPTION = "Minimal package list used for \
	1) minimal image for flashstation initramfs/initrd and \
	2) included in full image so this list does not need to be \
	maintained in two places"

LICENSE = "MIT"

PR = "4"

inherit packagegroup

RDEPENDS_${PN} = " \
	e2fsprogs-mke2fs \
	iw \
	kernel-devicetree \
	kernel-image \
	kernel-modules \
	sly-modules \
	slimline-modules \
	ntp \
	openssh-sftp-server \
	packagegroup-core-ssh-openssh \
	psoc5-verify \
	psoc-fw-sly \
	psoc-fw-slimline \
	strace \
	u-boot-fw-utils \
	u-boot-slimline \
	u-boot-script-slimline \
	util-linux-mkfs \
	util-linux-sfdisk \
	vivutils \
	memtester \
"
