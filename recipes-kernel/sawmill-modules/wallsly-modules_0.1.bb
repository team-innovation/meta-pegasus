DESCRIPTION = "Out of kernel modules for wallsly."
LICENSE = "CLOSED"

inherit module

SRC_URI = "git://git.vivint.com/slimline-modules;branch=wallsly"
SRCREV = "${AUTOREV}"
PV = "3.14.28+git${SRCPV}"
PR = "r3"

S = "${WORKDIR}/git"

PACKAGE_ARCH = "${MACHINE_ARCH}"
KERNEL_MODULE_PROBECONF += "monpwr-wallsly"
module_conf_monpwr-wallsly = "blacklist monpwr-wallsly"
