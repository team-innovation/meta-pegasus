# Copyright (C) 2016 Vivint, Inc.
DESCRIPTION = "packages needed just for slimline"
LICENSE = "MIT"

PR = "r0"

inherit packagegroup

RDEPENDS_${PN} += " \
	slimline-version \
"

