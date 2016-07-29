# Copyright (C) 2016 Vivint, Inc.
DESCRIPTION = "packages needed just for slimline"
LICENSE = "MIT"

PR = "r1"

inherit packagegroup

RDEPENDS_${PN} += " \
	slimline-version \
	psoc-fw-slimline \
"

