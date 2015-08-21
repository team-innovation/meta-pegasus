SRCBRANCH = "slimline-dizzy"
LOCALVERSION = "-1.0.0_slimline"
SRCREV = "${AUTOREV}"
KERNEL_SRC = "git://git.vivint.com/linux-imx.git;protocol=git"
SRC_URI = "${KERNEL_SRC};branch=${SRCBRANCH}"

PR = "r5"

DEFAULT_PREFERENCE = "1"

do_configure_prepend() {
   # copy latest defconfig slimline_defconfig to use
   cp ${S}/arch/arm/configs/slimline_defconfig ${S}/.config
   cp ${S}/arch/arm/configs/slimline_defconfig ${S}/../defconfig
   # HACK! overwrite imx_v7_defconfig so the above does not get
   # undone by this same task in linux-imx.bb
   cp ${S}/arch/arm/configs/slimline_defconfig ${S}/arch/arm/configs/imx_v7_defconfig
}

KERNEL_MODULE_PROBECONF += "rtl8192ce"
KERNEL_MODULE_AUTOLOAD += "rtl8192ce atmel_mxt_ts psoc_swd viv_iod monpwr"
module_conf_rtl8192ce = "options rtl8192ce ips=0 fwlps=0 swlps=0 swenc=0"
