# Copyright (C) 2014 Vivint, Inc.

DESCRIPTION = "packages necessary for initial board setup"
LICENSE = "MIT"

PR = "1"

inherit packagegroup

RDEPENDS_${PN} = " \
	ctailcat \
	e2fsprogs-mke2fs \
	iw \
	kernel-devicetree \
	kernel-image \
	ntp \
	openssh-sftp-server \
	packagegroup-core-ssh-openssh \
	psoc5-verify \
	psoc-fw \
	python3-ctypes \
	python3-distribute \
	python3-importlib \
	python3-jinja2 \
	python3-json \
	python3-math \
	python3-misc \
	python3-msgpack \
	python3-netserver \
	python3-pexpect-u \
	python3-re \
	python3-resource \
	python3-setproctitle \
	python3-shell \
	python3-sip \
	python3-sqlite3 \
	python3-subprocess \
	python3-terminal \
	python3-textutils \
	slimline-modules \
	sqlite3 \
	strace \
	touchlink-ntpsync \
	tzdata-americas \
	u-boot-fw-utils \
	u-boot-slimline \
	util-linux-mkfs \
	util-linux-sfdisk \
	wps-sh \
"
