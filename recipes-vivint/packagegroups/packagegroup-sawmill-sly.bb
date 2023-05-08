# Copyright (C) 2016 Vivint, Inc.
DESCRIPTION = "packages needed just for sly and future primary panels etc"
LICENSE = "MIT"

inherit packagegroup

RDEPENDS_${PN} += " \
	graphviz \
	openvpn \
        mosquitto \
        python3-paho-mqtt \
        python3-ubus \
"

