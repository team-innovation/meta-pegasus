# Copyright (C) 2016 Vivint, Inc.
DESCRIPTION = "packages needed just for sly and future primary panels etc"
LICENSE = "MIT"

PR = "r1"

inherit packagegroup

RDEPENDS_${PN} += " \
	simple-ntpdinit \
"

