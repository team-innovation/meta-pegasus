# Copyright (C) 2015 Vivint, Inc.

DESCRIPTION = "primary aka sly touchlink apps packages, framework, roubaix, etc"
LICENSE = "MIT"

PR = "r21"

require packagegroup-touchlink-apps-common.inc


RDEPENDS_${PN} += " \
	touchlink-apps-345d \
	touchlink-apps-cloudd \
	touchlink-apps-dbapd \
	touchlink-apps-httpd \
	touchlink-apps-mmpd \
	touchlink-apps-modemd \
	touchlink-apps-multiplexerd \
	touchlink-apps-netd \
	touchlink-apps-pyftpd \
	touchlink-apps-roubaix-images-png \
	touchlink-apps-rtspd \
	touchlink-apps-sundance \
	touchlink-apps-smarthomed \
	touchlink-apps-listenerd \
	touchlink-apps-test-daemon \
	touchlink-apps-test-ui \
	touchlink-apps-videod \
	touchlink-apps-webd \
	touchlink-apps-zwaved \
"

