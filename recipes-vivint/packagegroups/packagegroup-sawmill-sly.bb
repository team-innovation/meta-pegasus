# Copyright (C) 2016 Vivint, Inc.
DESCRIPTION = "packages needed just for sly and future primary panels etc"
LICENSE = "MIT"

PR = "r35"

require packagegroup-sawmill-sly-common.inc

RDEPENDS_${PN} += " \
        u-boot-wallsly \
	graphviz \
	nm-pkgs \
	openwrt-mt7620 \
	openwrt-rt3352 \
	mighty-gecko-fw-wallsly \
	wallsly-version \
	mosquitto \
        python3-paho-mqtt \
        telegraf \
	vis \
"

