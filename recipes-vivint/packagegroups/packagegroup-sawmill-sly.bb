# Copyright (C) 2016 Vivint, Inc.
DESCRIPTION = "packages needed just for sly and future primary panels etc"
LICENSE = "MIT"

PR = "r36"

require packagegroup-sawmill-sly-common.inc

RDEPENDS_${PN} += " \
        u-boot-wallsly \
	graphviz \
	openwrt-mt7620 \
        nm-pkgs \
	mighty-gecko-fw-wallsly \
	wallsly-version \
	mosquitto \
        python3-paho-mqtt \
        telegraf \
	vis \
"

