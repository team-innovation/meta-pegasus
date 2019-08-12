DESCRIPTION = "Out of kernel modules for wallsly."
LICENSE = "CLOSED"

inherit module

GIT_MONPWR_SERVER_WALLSLY ?= "${GIT_SERVER}"
GIT_MONPWR_BRANCH_WALLSLY ?= "wallsly-develop"
GIT_MONPWR_REV_WALLSLY ?= "${AUTOREV}"
GIT_MONPWR_PROTOCOL_WALLSLY ?= "ssh"

SRC_URI = "git://${GIT_MONPWR_SERVER_WALLSLY}/slimline-modules;protocol=${GIT_MONPWR_PROTOCOL_WALLSLY};branch=${GIT_MONPWR_BRANCH_WALLSLY}"
SRCREV = "${GIT_MONPWR_REV_WALLSLY}"
PV = "3.14.28+git${SRCPV}"
PR = "r14"

S = "${WORKDIR}/git"

PACKAGE_ARCH = "${MACHINE_ARCH}"
KERNEL_MODULE_PROBECONF += "monpwr-wall"
module_conf_monpwr-wall = "blacklist monpwr-wall"
MODULES_MODULE_SYMVERS_LOCATION = "monpwr"
