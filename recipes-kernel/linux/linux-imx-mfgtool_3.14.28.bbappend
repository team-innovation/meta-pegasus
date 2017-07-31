GIT_KERNEL_BRANCH ?= "develop"
LOCALVERSION = "-1.0.0_slimline"
SRCREV = "${AUTOREV}"
GIT_KERNEL_SERVER ?= "${GIT_SERVER}"
KERNEL_SRC = "git://${GIT_KERNEL_SERVER}/linux-imx;protocol=ssh"
SRC_URI = "${KERNEL_SRC};branch=${GIT_KERNEL_BRANCH}"
PV = "3.14.28+git${SRCPV}"

PR = "r21"

DEFAULT_PREFERENCE = "1"

do_configure_prepend() {
   # copy latest defconfig slimline_defconfig to use
   cp ${S}/arch/arm/configs/slimline_mfg_defconfig ${S}/.config
   cp ${S}/arch/arm/configs/slimline_mfg_defconfig ${S}/../defconfig
   # HACK! overwrite imx_v7_defconfig so the above does not get
   # undone by this same task in linux-imx.bb
   cp ${S}/arch/arm/configs/slimline_mfg_defconfig ${S}/arch/arm/configs/imx_v7_mfg_defconfig
}
