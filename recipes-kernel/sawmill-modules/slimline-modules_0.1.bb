DESCRIPTION = "Out of kernel modules for slimline."
LICENSE = "CLOSED"

inherit module

GIT_SERVER_MONPWR ?= "${GIT_SERVER}"
GIT_MONPWR_BRANCH_SLIMLINE ?= "release/3.10.5"

SRC_URI = "git://${GIT_SERVER_MONPWR}/slimline-modules;protocol=ssh;branch=${GIT_MONPWR_BRANCH_SLIMLINE}"
SRCREV = "${AUTOREV}"
PV = "3.14.28+git${SRCPV}"
PR = "r4"

S = "${WORKDIR}/git"

PACKAGE_ARCH = "${MACHINE_ARCH}"
KERNEL_MODULE_PROBECONF += "monpwr"
module_conf_monpwr = "blacklist monpwr"
