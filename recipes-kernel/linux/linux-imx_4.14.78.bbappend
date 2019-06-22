GIT_KERNEL_BRANCH = "vivint/kernel-4.14"
GIT_KERNEL_SERVER = "${GIT_SERVER}"
GIT_KERNEL_PROTOCOL = "ssh"
GIT_KERNEL_REV = "${AUTOREV}"
SRCREV = "${GIT_KERNEL_REV}"
KERNEL_SRC = "git://${GIT_KERNEL_SERVER}/linux-imx;protocol=${GIT_KERNEL_PROTOCOL}"
SRC_URI = "${KERNEL_SRC};branch=${GIT_KERNEL_BRANCH}\
	   file://defconfig"
FILESEXTRAPATHS_prepend := "${THISDIR}/:"
PV = "4.14.78-+git${SRCPV}"

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

PR = "15"

DO_CONFIG_V7_COPY = "yes"

do_copy_defconfig_prepend() {
   # copy latest defconfig slimline_defconfig to use
   install -m 0755 ${S}/arch/arm/configs/slimline_defconfig ${S}/.config
   install -m 0755 ${S}/arch/arm/configs/slimline_defconfig ${S}/../defconfig
   # HACK! overwrite imx_v7_defconfig so the above does not get
   # undone by this same task in linux-imx.bb
   install -m 0755 ${S}/arch/arm/configs/slimline_defconfig ${S}/arch/arm/configs/imx_v7_defconfig
}

do_compile_prepend() {
   # Clean kernel 
   cd ${STAGING_KERNEL_DIR}
   oe_runmake mrproper
   cd -

}

do_install_append() {
    cd ${D}/${KERNEL_IMAGEDEST}/ ; md5sum ${KERNEL_IMAGETYPE}-${KERNEL_VERSION} > ${S}/${KERNEL_IMAGETYPE}.md5sum ; cd -
    install ${S}/${KERNEL_IMAGETYPE}.md5sum ${D}/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.md5sum
}

FILES_${KERNEL_PACKAGE_NAME}-base += "${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.md5sum"

KERNEL_MODULE_PROBECONF += "rtl8192ce snd-soc-cx20704 snd-soc-imx-cx20704 snd-soc-gsm030x snd-soc-imx-gsm030x ath9k cfg80211"
KERNEL_MODULE_AUTOLOAD += "atmel_mxt_ts psoc_swd viv_iod"
module_conf_rtl8192ce = "options rtl8192ce ips=0 fwlps=0 swlps=0 swenc=1"
module_conf_snd-soc-cx20704 = "blacklist snd-soc-cx20704"
module_conf_snd-soc-imx-cx20704 = "blacklist snd-soc-imx-cx20704"
module_conf_snd-soc-gsm030x = "blacklist snd-soc-gsm030x"
module_conf_snd-soc-imx-gsm030x = "blacklist snd-soc-imx-gsm030x"
module_conf_cfg80211 = "options cfg80211 ieee80211_regdom=US"
module_conf_ath9k = "options ath9k nohwcrypt=1"
