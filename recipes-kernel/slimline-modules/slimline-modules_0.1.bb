DESCRIPTION = "Out of kernel modules for slimline."
LICENSE = "CLOSED"

inherit module

PR = "r1"
PV = "0.1"

SRC_URI = " \
	git://git.vivint.com/slimline-modules;branch=master \
"

SRCREV = "${AUTOREV}"

S = "${WORKDIR}/git"

PACKAGE_ARCH = "${MACHINE_ARCH}"
