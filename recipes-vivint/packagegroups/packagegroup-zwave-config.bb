# Copyright (C) 2016 Vivint, Inc.

DESCRIPTION = "packages for zwave config"
LICENSE = "MIT"

PR = "r1"

inherit packagegroup

RDEPENDS_${PN} += " \
	zwave-config-500-series \
"

