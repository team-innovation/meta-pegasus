# Copyright (C) 2016 Vivint, Inc.
DESCRIPTION = "packages needed just for sly and future primary panels etc"
LICENSE = "MIT"

PR = "r37"

require packagegroup-sawmill-sly-common.inc

RDEPENDS_${PN} += " \
        u-boot-wallsly \
	graphviz \
	openvpn \
	openvpn-procmand \
	openwrtlegacy-mt7620 \
	openwrtlatest-mt7620 \
        nm-pkgslegacy \
        nm-pkgslatest \
	mighty-gecko-fw-wallsly \
	wallsly-version \
	mosquitto \
        python3-paho-mqtt \
        telegraf \
	vis \
"

