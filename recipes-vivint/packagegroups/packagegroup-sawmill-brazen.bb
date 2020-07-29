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
    zeroconf \
    python3-avahi \
    python3-dbus \
    mmc-utils \
	graphviz \
	mighty-gecko-fw-wallsly \
	mosquitto \
        python3-paho-mqtt \
        telegraf \
	vis \
	bluetooth-fw \
	efr32-fw \
        zwave-fw \
        iot-radio-fw-verify \
"

