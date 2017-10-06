DESCRIPTION = "Out of kernel modules for slimline."
LICENSE = "CLOSED"

inherit module
GIT_SERVER_MONPWR ?= "${GIT_SERVER}"
GIT_MONPWR_BRANCH_SKYHUB ?= "release/skyhub-3.10.6"

SRC_URI = "git://${GIT_SERVER_MONPWR}/slimline-modules;protocol=ssh;branch=${GIT_MONPWR_BRANCH_SKYHUB}"
SRCREV = "${AUTOREV}"
PV = "3.14.28+git${SRCPV}"
PR = "r3"

S = "${WORKDIR}/git"

PACKAGE_ARCH = "${MACHINE_ARCH}"
KERNEL_MODULE_PROBECONF += "monpwr-sly"
module_conf_monpwr-sly = "blacklist monpwr-sly"
