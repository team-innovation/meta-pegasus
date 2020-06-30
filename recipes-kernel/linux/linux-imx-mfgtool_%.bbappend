GIT_KERNEL_BRANCH ?= "vivint/kernel-4.14"
LOCALVERSION = "-1.0.0-slimline"
GIT_KERNEL_SERVER ?= "${GIT_SERVER}"
GIT_KERNEL_PROTOCOL ?= "ssh"
GIT_KERNEL_REV ?= "${AUTOREV}"
SRCREV = "${GIT_KERNEL_REV}"
LINUX_IMX_REPO ?= "linux-imx"

KERNEL_SRC = "git://${GIT_KERNEL_SERVER}/${LINUX_IMX_REPO};protocol=${GIT_KERNEL_PROTOCOL}"
SRC_URI = "${KERNEL_SRC};branch=${GIT_KERNEL_BRANCH} \
	   file://defconfig"

FILESEXTRAPATHS_prepend := "${THISDIR}/:" 
DEFAULT_PREFERENCE = "1"

SRCBRANCH = "sumo" 

PV = "4.14.78-+git${SRCPV}"
PR = "r01"

do_configure_prepend() {
   # copy latest defconfig slimline_defconfig to use
   install -m 0755 ${S}/arch/arm/configs/slimline_mfg_defconfig ${S}/.config
   install -m 0755 ${S}/arch/arm/configs/slimline_mfg_defconfig ${S}/../defconfig
   # HACK! overwrite imx_v7_defconfig so the above does not get
   # undone by this same task in linux-imx.bb
   install -m 0755 ${S}/arch/arm/configs/slimline_mfg_defconfig ${S}/arch/arm/configs/imx_v7_mfg_defconfig
}

do_compile_prepend() {
   # Clean kernel 
   cd ${STAGING_KERNEL_DIR}
   oe_runmake mrproper
   cd -
}
