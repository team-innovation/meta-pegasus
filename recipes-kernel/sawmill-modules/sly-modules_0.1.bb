DESCRIPTION = "Out of kernel modules for slimline."
LICENSE = "CLOSED"

inherit module
GIT_MONPWR_SERVER_SKYHUB ?= "${GIT_SERVER}"
GIT_MONPWR_BRANCH_SKYHUB ?= "skyhub-develop"
GIT_MONPWR_REV_SKYHUB ?= "${AUTOREV}"
GIT_MONPWR_PROTOCOL_SKYHUB ?= "ssh"

SRC_URI = "git://${GIT_MONPWR_SERVER_SKYHUB}/slimline-modules;protocol=${GIT_MONPWR_PROTOCOL_SKYHUB};branch=${GIT_MONPWR_BRANCH_SKYHUB}"
SRCREV = "${GIT_MONPWR_REV_SKYHUB}"
PV = "3.14.28+git${SRCPV}"
PR = "r3"

S = "${WORKDIR}/git"

PACKAGE_ARCH = "${MACHINE_ARCH}"
KERNEL_MODULE_PROBECONF += "monpwr-sly"
module_conf_monpwr-sly = "blacklist monpwr-sly"
MODULES_MODULE_SYMVERS_LOCATION = "monpwr"
