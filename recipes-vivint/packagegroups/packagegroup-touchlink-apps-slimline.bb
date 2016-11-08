# Copyright (C) 2015 Vivint, Inc.

DESCRIPTION = "slimline aka primary panel touchlink apps packages, framework, roubaix, etc"
LICENSE = "MIT"

PR = "r16"

require packagegroup-touchlink-apps-common.inc

RDEPENDS_${PN} += " \
	touchlink-apps-roubaix-images-png \
"

