# Copyright (C) 2016 Vivint, Inc.
DESCRIPTION = "packages needed just for sly and future primary panels etc"
LICENSE = "MIT"

PR = "r3"

require packagegroup-sawmill-sly-common.inc

RDEPENDS_${PN} += " \
        u-boot-wallsly \
        u-boot-brazen \
        mmc-utils \
	acm-usb-v6 \
	easy-rsa \
	fabric-go \
	lxfp \
	openvpn \
	openwrt-mt7620 \
	psoc-fw-sly \
	psoc-fw-sly-fcc \
	psoc-fw-wallsly \
	qflash \
	qfotatool \
	nm-pkgs \
	sly-utils \
	brazen-version \
	swdltool \
	zwave-nvm-converter \
	touchlink-kwikset-firmware \
	touchlink-rtc-firmware \
	touchlink-rcs-firmware \
	touchlink-telit-firmware-he910 \
	touchlink-telit-firmware-ue910 \
	touchlink-sierra-firmware-hl7588-a \
	touchlink-sierra-firmware-hl7588-v \
	touchlink-quectel-firmware-eg91-f \
	touchlink-quectel-firmware-eg91-version \
	vaudio-wallsly \
	mfr-audio-test-wallsly \
	mfr-piezo-test \
	asr-parse \
        python3-ubus \
	python3-paho-mqtt \
"

