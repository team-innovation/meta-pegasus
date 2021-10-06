# Copyright (C) 2020 Vivint, Inc.
DESCRIPTION = "packages needed just for sly and future primary panels etc"
LICENSE = "MIT"

PR = "r5"

inherit packagegroup

RDEPENDS_${PN} += " \
	acm-usb-v6 \
	bzip2 \
	easy-rsa \
	fabric-go \
	lxfp \
	openvpn \
	psoc-fw-sly \
	psoc-fw-sly-fcc \
	psoc-fw-wallsly \
	qflash \
	qfotatool \
	hub-utils \
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
	touchlink-keypad-firmware \
	vaudio-wallsly \
	mfr-audio-test-wallsly \
	mfr-piezo-test \
	asr-parse \
    python3-ubus \
    python3-avahi \
    python3-dbus \
"

