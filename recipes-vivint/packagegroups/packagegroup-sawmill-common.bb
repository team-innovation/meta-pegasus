# Copyright (C) 2014 Vivint, Inc.

DESCRIPTION = "Common packages needed above the minimum "
LICENSE = "MIT"

PR = "22"

inherit packagegroup

# Note:
#   If a package is also needed for minimum image
#   then add it to packagegroup-sawmill-min not here.
RDEPENDS_${PN} = " \
	alsa-utils \
	ca-certificates \
	kernel-module-mt7603 \
	libsodium18 \
	openvpn \
	packagegroup-sawmill-debugtools \
	packagegroup-sawmill-min \
	procps \
	python3-cachetools \
	python3-ctypes \
	python3-distribute \
	python3-importlib \
	python3-intelhex \
	python3-jinja2 \
	python3-json \
	python3-markupsafe \
	python3-math \
	python3-misc \
	python3-msgpack \
	python3-netserver \
	python3-nfcpy \
	python3-numpy \
	python3-pexpect-u \
	python3-pyserial \
	python3-pytz \
	python3-re \
	python3-resource \
	python3-setproctitle \
	python3-shell \
	python3-sip \
	python3-sqlite3 \
	python3-subprocess \
	python3-terminal \
	python3-textutils \
	python3-threading \
	speedtest-cli \
	sqlite3 \
	touchlink-ntpsync \
	tzdata \
	tzdata-americas \
	util-linux \
	udev-rules-sawmill \
	vim \
	webrtc-audio-processing \
	wps-sh \
    busybox-httpd \
"
