DESCRIPTION = "Out of kernel modules for wallsly."
LICENSE = "CLOSED"

inherit module

SRC_URI = "git://${GIT_SERVER}/slimline-modules.git;protocol=ssh;branch=wallsly-master"
SRCREV = "${AUTOREV}"
PV = "3.14.28+git${SRCPV}"
PR = "r13"

S = "${WORKDIR}/git"

PACKAGE_ARCH = "${MACHINE_ARCH}"
KERNEL_MODULE_PROBECONF += "monpwr-wall"
module_conf_monpwr-wall = "blacklist monpwr-wall"
