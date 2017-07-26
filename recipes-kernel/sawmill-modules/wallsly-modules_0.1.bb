DESCRIPTION = "Out of kernel modules for wallsly."
LICENSE = "CLOSED"

inherit module

GIT_SERVER_MONPWR ?= "${GIT_SERVER}"
GIT_MONPWR_BRANCH ?= "develop"

SRC_URI = "git://${GIT_SERVER_MONPWR}/slimline-modules;protocol=ssh;branch=wallsly-${GIT_MONPWR_BRANCH}"
SRCREV = "${AUTOREV}"
PV = "3.14.28+git${SRCPV}"
PR = "r13"

S = "${WORKDIR}/git"

PACKAGE_ARCH = "${MACHINE_ARCH}"
KERNEL_MODULE_PROBECONF += "monpwr-wall"
module_conf_monpwr-wall = "blacklist monpwr-wall"
