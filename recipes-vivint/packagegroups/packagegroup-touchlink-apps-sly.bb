# Copyright (C) 2015 Vivint, Inc.

DESCRIPTION = "primary aka sly touchlink apps packages, framework, roubaix, etc"
LICENSE = "MIT"

PR = "r13"

require packagegroup-touchlink-apps-common.inc


RDEPENDS_${PN} += " \
	touchlink-apps-345d \
	touchlink-apps-cloudd \
	touchlink-apps-dbapd \
	touchlink-apps-httpd \
	touchlink-apps-modemd \
	touchlink-apps-multiplexerd \
	touchlink-apps-netd \
	touchlink-apps-pyftpd \
	touchlink-apps-roubaix-images-png \
	touchlink-apps-roubaix-images-svg \
	touchlink-apps-rtspd \
	touchlink-apps-sundance \
	touchlink-apps-videod \
	touchlink-apps-webd \
	touchlink-apps-zwaved \
"

