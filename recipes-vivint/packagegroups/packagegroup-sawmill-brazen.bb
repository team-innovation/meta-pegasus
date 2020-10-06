# Copyright (C) 2016 Vivint, Inc.
DESCRIPTION = "packages needed just for sly and future primary panels etc"
LICENSE = "MIT"

PR = "r13"

require packagegroup-sawmill-sly-common.inc

RDEPENDS_${PN} += " \
    	u-boot-wallsly \
    	u-boot-brazen \
	brazen-version \
	hostapd \
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
"

### TODO - remove this later for real HUB+
RDEPENDS_${PN} += " \ 
		touchlink-maxtouch-config \
"
