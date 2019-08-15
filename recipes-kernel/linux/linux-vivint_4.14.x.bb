# Adapted from linux-imx.inc, copyright (C) 2013, 2014 O.S. Systems Software LTDA
# Released under the MIT license (see COPYING.MIT for the terms)

require recipes-kernel/linux/linux-imx.inc

SUMMARY = "Linux kernel for Vivint boards"
GIT_KERNEL_BRANCH = "feature/4.14.98_2.0.0_ga"
GIT_KERNEL_SERVER = "${GIT_SERVER}"
GIT_KERNEL_PROTOCOL = "ssh"
GIT_KERNEL_REV = "${AUTOREV}"
SRCREV = "${GIT_KERNEL_REV}"
KERNEL_SRC = "git://${GIT_KERNEL_SERVER}/linux-imx;protocol=${GIT_KERNEL_PROTOCOL}"
SRC_URI = "${KERNEL_SRC};branch=${GIT_KERNEL_BRANCH}"

LOCALVERSION = "-2.0.0-ga+yocto"
DEPENDS += "lzop-native bc-native"
COMPATIBLE_MACHINE = "(mx6|mx7|mx8)"

KERNEL_DEFCONFIG = "slimline_defconfig"

do_install_append() {
    cd ${D}/${KERNEL_IMAGEDEST}/ 
    md5sum ${KERNEL_IMAGETYPE}-${KERNEL_VERSION} > ${S}/${KERNEL_IMAGETYPE}.md5sum  
    cd -
    install ${S}/${KERNEL_IMAGETYPE}.md5sum ${D}/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.md5sum
}

FILES_${KERNEL_PACKAGE_NAME}-base += "${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.md5sum"

KERNEL_MODULE_PROBECONF += "rtl8192ce snd-soc-cx20704 snd-soc-imx-cx20704 snd-soc-gsm030x snd-soc-imx-gsm030x ath9k cfg80211"
KERNEL_MODULE_AUTOLOAD += "atmel_mxt_ts psoc_swd viv_iod dummy"
module_conf_rtl8192ce = "options rtl8192ce ips=0 fwlps=0 swlps=0 swenc=1"
module_conf_snd-soc-cx20704 = "blacklist snd-soc-cx20704"
module_conf_snd-soc-imx-cx20704 = "blacklist snd-soc-imx-cx20704"
module_conf_snd-soc-gsm030x = "blacklist snd-soc-gsm030x"
module_conf_snd-soc-imx-gsm030x = "blacklist snd-soc-imx-gsm030x"
module_conf_cfg80211 = "options cfg80211 ieee80211_regdom=US"
module_conf_ath9k = "options ath9k nohwcrypt=1"
