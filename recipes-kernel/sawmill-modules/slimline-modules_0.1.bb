DESCRIPTION = "Out of kernel modules for slimline."
LICENSE = "CLOSED"

inherit module

SRC_URI = "git://git.vivint.com/slimline-modules;branch=master"
SRCREV = "${AUTOREV}"
PV = "3.14.28+git${SRCPV}"
PR = "r1"

S = "${WORKDIR}/git"

PACKAGE_ARCH = "${MACHINE_ARCH}"
KERNEL_MODULE_PROBECONF += "monpwr"
module_conf_monpwr = "blacklist monpwr"
