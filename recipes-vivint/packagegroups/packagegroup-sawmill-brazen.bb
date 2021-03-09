# Copyright (C) 2016 Vivint, Inc.
DESCRIPTION = "packages needed just for sly and future primary panels etc"
LICENSE = "MIT"

PR = "r16"

require packagegroup-sawmill-sly-common.inc

RDEPENDS_${PN} += " \
    	u-boot-wallsly \
    	u-boot-brazen \
	brazen-version \
    	zeroconf \
    	python3-avahi \
    	python3-dbus \
	python3-evdev \
    	mmc-utils \
	graphviz \
	mighty-gecko-fw-wallsly \
	mosquitto \
        python3-paho-mqtt \
        telegraf \
	vis \
	iot-fw-efr32 \
	iot-fw-ble \
	iot-fw-zwave \
        iot-radio-fw-verify \
	mt7663e-driver \
	openvpn-procmand \
        avahi-utils \
	libavahi-client3 \
"
