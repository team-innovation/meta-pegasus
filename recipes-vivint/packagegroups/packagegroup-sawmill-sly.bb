# Copyright (C) 2016 Vivint, Inc.
DESCRIPTION = "packages needed just for sly and future primary panels etc"
LICENSE = "MIT"

PR = "r26"

inherit packagegroup

RDEPENDS_${PN} += " \
	acm-usb-v6 \
	easy-rsa \
	fabric-go \
	openvpn \
	openwrt-mt7620 \
	openwrt-rt3352 \
	psoc-fw-sly \
	psoc-fw-sly-fcc \
	psoc-fw-wallsly \
	mighty-gecko-fw-wallsly \
	sly-utils \
	sly-version \
	wallsly-version \
	swdltool \
	touchlink-kwikset-firmware \
	touchlink-rtc-firmware \
	touchlink-telit-firmware-he910 \
	touchlink-telit-firmware-ue910 \
	touchlink-sierra-firmware-hl7588-a \
	touchlink-sierra-firmware-hl7588-v \
	vaudio-wallsly \
	mfr-audio-test-wallsly \
	mfr-piezo-test \
	modem-loopback \
	asr-parse \
"

