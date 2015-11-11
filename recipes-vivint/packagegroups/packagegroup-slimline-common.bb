# Copyright (C) 2014 Vivint, Inc.

DESCRIPTION = "Common packages needed above the minimum "
LICENSE = "MIT"

PR = "10"

inherit packagegroup

# Note:
#   If a package is also needed for minimum image
#   then add it to packagegroup-slimline-min not here.
RDEPENDS_${PN} = " \
	libsodium13 \
	kernel-module-mt7603 \
	packagegroup-slimline-min \
	python3-ctypes \
	python3-distribute \
	python3-importlib \
	python3-intelhex \
	python3-jinja2 \
	python3-json \
	python3-math \
	python3-misc \
	python3-msgpack \
	python3-netserver \
	python3-numpy \
	python3-pexpect-u \
	python3-re \
	python3-resource \
	python3-setproctitle \
	python3-shell \
	python3-sip \
	python3-sqlite3 \
	python3-subprocess \
	python3-terminal \
	python3-textutils \
	slimline-modules \
	slimline-version \
	sqlite3 \
	touchlink-ntpsync \
	tzdata-americas \
	wps-sh \
	alsa-utils \
"
