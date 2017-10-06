DESCRIPTION = "Out of kernel modules for wallsly."
LICENSE = "CLOSED"

inherit module

GIT_SERVER_MONPWR ?= "${GIT_SERVER}"
GIT_MONPWR_BRANCH_WALLSLY ?= "release/wallsly-3.10.6"

SRC_URI = "git://${GIT_SERVER_MONPWR}/slimline-modules;protocol=ssh;branch=${GIT_MONPWR_BRANCH_WALLSLY}"
SRCREV = "${AUTOREV}"
PV = "3.14.28+git${SRCPV}"
PR = "r14"

S = "${WORKDIR}/git"

PACKAGE_ARCH = "${MACHINE_ARCH}"
KERNEL_MODULE_PROBECONF += "monpwr-wall"
module_conf_monpwr-wall = "blacklist monpwr-wall"
