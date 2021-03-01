# Copyright (C) 2015 Vivint, Inc.

DESCRIPTION = "Brazen system packages"
LICENSE = "MIT"

PR = "r3"

require packagegroup-touchlink-apps-base.inc
#require packagegroup-touchlink-qt-brazen.inc

RDEPENDS_${PN} += " \
	touchlink-apps-345d \
	touchlink-apps-audmgrd \
    touchlink-apps-baguette \
	touchlink-apps-cloudd \
	touchlink-apps-dbapd \
	touchlink-apps-httpd \
	touchlink-apps-huei \
	touchlink-apps-sundance \
	touchlink-apps-mmpd \
	touchlink-apps-modemd \
	touchlink-apps-pyftpd \
	touchlink-apps-rtspd \
	touchlink-apps-test-daemon \
	touchlink-apps-videod \
	touchlink-apps-webd \
	touchlink-apps-zwaved \
	"
