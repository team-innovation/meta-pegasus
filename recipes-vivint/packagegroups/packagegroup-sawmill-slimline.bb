# Copyright (C) 2016 Vivint, Inc.
DESCRIPTION = "packages needed just for slimline"
LICENSE = "MIT"

PR = "r2"

inherit packagegroup

RDEPENDS_${PN} += " \
	slimline-utils \
	slimline-version \
	psoc-fw-slimline \
"

