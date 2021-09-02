# Copyright (C) 2015 Vivint, Inc.

DESCRIPTION = "primary aka sly touchlink apps packages, framework, roubaix, etc"
LICENSE = "MIT"

PR = "r37"

require packagegroup-touchlink-apps-common.inc


RDEPENDS_${PN} += " \
	touchlink-apps-345d \
	touchlink-apps-audmgrd \
	touchlink-apps-cloudd \
	touchlink-apps-dbapd \
	touchlink-apps-flashpolicyd \
	touchlink-apps-huei \
	touchlink-apps-mmpd \
	touchlink-apps-modemd \
	touchlink-apps-modemd-proxies \
	touchlink-apps-sundance \
	touchlink-apps-netd \
	touchlink-apps-pyftpd \
	touchlink-apps-rtspd \
	touchlink-apps-sundance \
	touchlink-apps-test-daemon \
	touchlink-apps-videod \
	touchlink-apps-webd \
	touchlink-apps-zwaved \
	touchlink-apps-test-ui \
"
