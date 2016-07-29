# Copyright (C) 2016 Vivint, Inc.
DESCRIPTION = "packages needed just for sly and future primary panels etc"
LICENSE = "MIT"

PR = "r4"

inherit packagegroup

RDEPENDS_${PN} += " \
	sly-version \
	psoc-fw-sly \
	psoc-fw-sly-fcc \
"

