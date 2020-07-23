# Copyright (C) 2016 Vivint, Inc.
DESCRIPTION = "packages needed just for sly and future primary panels etc"
LICENSE = "MIT"

PR = "r8"

require packagegroup-sawmill-sly-common.inc

RDEPENDS_${PN} += " \
        u-boot-wallsly \
        u-boot-brazen \
	brazen-version \
	hostapd \
        mmc-utils \
	graphviz \
	openwrt-rt3352 \
	mighty-gecko-fw-wallsly \
	mosquitto \
        python3-paho-mqtt \
        telegraf \
	vis \
	bluetooth-mfg-fw \
	efr32-mfg-fw \
        zwave-mfg-fw \
        iot-radio-fw-verify \
"

