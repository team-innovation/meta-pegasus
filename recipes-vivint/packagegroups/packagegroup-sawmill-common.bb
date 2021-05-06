# Copyright (C) 2014 Vivint, Inc.

DESCRIPTION = "Common packages needed above the minimum "
LICENSE = "MIT"

PR = "55"

inherit packagegroup

# Note:
#   If a package is also needed for minimum image
#   then add it to packagegroup-sawmill-min not here.
RDEPENDS_${PN} = " \
	alsa-utils \
	ca-certificates \
	civetweb \
	cronie \
	gnupg \
	iotivity-resource \
	iotivity-bridging-plugins \
	libsodium18 \
	logrotate \
	lookbusy \
	packagegroup-sawmill-debugtools \
	packagegroup-sawmill-min \
	procps \
	python3-asn1crypto \
	python3-asyncio \
	python3-aioconsole \
	python3-aiodns \
	python3-aiohttp \
	python3-aiohttp-security \
	python3-aiohttp-session \
	python3-aiohttp-sse \
	python3-async-generator \
	python3-async-timeout \
	python3-atomicwrites \
	python3-attrs \
	python3-bson \
	python3-cachetools \
	python3-cchardet \
	python3-cffi \
	python3-compile \
	python3-cryptography \
	python3-ctypes \
	python3-curses \
	python3-cstruct \
	python3-cython \
	python3-gdbm \
	python3-grpcio \
	python3-idna \
	python3-idna-ssl \
	python3-pycares \
	python3-terminaltables \
	python3-idle \
	python3-importlib \
	python3-intelhex \
	python3-jinja2 \
	python3-json \
	python3-2to3 \
	python3-mailbox \
	python3-markupsafe \
	python3-math \
	python3-misc \
	python3-msgpack \
	python3-multidict \
	python3-more-itertools \
	python3-netserver \
	python3-nfcpy \
	python3-numpy \
	python3-pathlib2 \
	python3-pexpect \
	python3-profile \
	python3-pluggy \
	python3-protobuf \
	python3-pycparser \
	python3-pyftpdlib \
	python3-pyjwt \
	python3-pyserial \
	python3-pyssh-ctypes \
	python3-pytest \
	python3-pytest-asyncio \
	python3-pytest-cov \
	python3-pyalsaaudio \
	python3-py \
	python3-pyopenssl \
	python3-pytz \
	python3-re \
	python3-resource \
	python3-setproctitle \
	python3-setuptools \
	python3-shell \
	python3-sip \
	python3-sqlite3 \
	python3-subprocess \
	python3-terminal \
	python3-typing \
	python3-typing-extensions \
	python3-textutils \
	python3-threading \
	python3-tkinter \
	python3-toolz \
	python3-transitions \
	python3-uvloop \
	python3-unixadmin \
	python3-numbers \
	python3-wheel \
	python3-yarl \
	pv \
	speedtest-cli \
	sqlite3 \
	touchlink-ntpsync \
	tzdata \
	tzdata-americas \
	util-linux \
	vim \
	webrtc-audio-processing \
	panel-audio-test \
	pa-test \
	pa-test-simple \
	tonegen \
	sndfix \
	wps-sh \
	xdelta3 \
	xz \
    touchlink-maxtouch-config \
"
