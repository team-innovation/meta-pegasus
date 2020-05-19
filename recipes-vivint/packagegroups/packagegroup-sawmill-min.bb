# Copyright (C) 2014 Vivint, Inc.
SUMMARY = "Minimal list of packages for a usable image"
DESCRIPTION = "Minimal package list used for \
	1) minimal image for flashstation initramfs/initrd and \
	2) included in full image so this list does not need to be \
	maintained in two places"

LICENSE = "MIT"

PR = "22"

inherit packagegroup

RDEPENDS_${PN} = " \
	chrony \
	chronyc \
	dosfstools \
	e2fsprogs-dumpe2fs \
	e2fsprogs-resize2fs \
	e2fsprogs-mke2fs \
	iw \
	kernel-devicetree \
	kernel-image \
	kernel-modules \
	memtester \
	obp-utils \
	openssh-sftp-server \
	packagegroup-core-ssh-openssh \
	psoc5-verify \
	mighty-gecko-verify \
	slimline-modules \
	sly-modules \
	strace \
	u-boot-fw-utils \
	u-boot-script-slimline \
	util-linux-mkfs \
	util-linux-sfdisk \
	vivutils \
	wallsly-modules \
"
