# Copyright (C) 2014 Vivint, Inc.

DESCRIPTION = "packages necessary for initial board setup"
LICENSE = "MIT"

PR = "3"

inherit packagegroup

# Note if a package is also needed for mfg flash station
# then add it to packagegroup-slimline-mfg not here

RDEPENDS_${PN} = " \
	packagegroup-slimline-mfg \
	python3-ctypes \
	python3-distribute \
	python3-importlib \
	python3-jinja2 \
	python3-json \
	python3-math \
	python3-misc \
	python3-msgpack \
	python3-netserver \
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
	sqlite3 \
	touchlink-ntpsync \
	tzdata-americas \
	wps-sh \
"
