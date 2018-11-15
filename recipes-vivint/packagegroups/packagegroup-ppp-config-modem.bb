# Copyright (C) 2016 Vivint, Inc.

DESCRIPTION = "packages for modem ppp config"
LICENSE = "MIT"

PR = "r2"

inherit packagegroup

RDEPENDS_${PN} += " \
	ppp-config-novatel-cdma \
	ppp-config-novatel-hs3002 \
	ppp-config-telit-ce910 \
	ppp-config-telit-he910 \
	ppp-config-telit-ue910 \
	ppp-config-quectel-eg91 \
"

