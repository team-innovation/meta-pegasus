GIT_KERNEL_BRANCH ?= "release/3.10.5"
LOCALVERSION = "-1.0.0_slimline"
SRCREV = "${AUTOREV}"
GIT_KERNEL_SERVER ?= "${GIT_SERVER}"
GIT_KERNEL_PROTOCOL ?= "ssh"
KERNEL_SRC = "git://${GIT_KERNEL_SERVER}/linux-imx;protocol=${GIT_KERNEL_PROTOCOL}"
SRC_URI = "${KERNEL_SRC};branch=${GIT_KERNEL_BRANCH}"
PV = "3.14.28+git${SRCPV}"

PR = "r51"

DEFAULT_PREFERENCE = "1"

do_configure_prepend() {
   # copy latest defconfig slimline_defconfig to use
   cp ${S}/arch/arm/configs/slimline_defconfig ${S}/.config
   cp ${S}/arch/arm/configs/slimline_defconfig ${S}/../defconfig
   # HACK! overwrite imx_v7_defconfig so the above does not get
   # undone by this same task in linux-imx.bb
   cp ${S}/arch/arm/configs/slimline_defconfig ${S}/arch/arm/configs/imx_v7_defconfig
}

do_install_append() {
	cd ${D}/${KERNEL_IMAGEDEST}/ ; md5sum ${KERNEL_IMAGETYPE}-${KERNEL_VERSION} > ${S}/${KERNEL_IMAGETYPE}.md5sum ; cd -
	install ${S}/${KERNEL_IMAGETYPE}.md5sum ${D}/${KERNEL_IMAGEDEST}/${KERNEL_IMAGETYPE}.md5sum
}

KERNEL_MODULE_PROBECONF += "rtl8192ce snd-soc-cx20704 snd-soc-imx-cx20704 snd-soc-gsm030x snd-soc-imx-gsm030x ath9k cfg80211"
KERNEL_MODULE_AUTOLOAD += "atmel_mxt_ts psoc_swd viv_iod"
module_conf_rtl8192ce = "options rtl8192ce ips=0 fwlps=0 swlps=0 swenc=1"
module_conf_snd-soc-cx20704 = "blacklist snd-soc-cx20704"
module_conf_snd-soc-imx-cx20704 = "blacklist snd-soc-imx-cx20704"
module_conf_snd-soc-gsm030x = "blacklist snd-soc-gsm030x"
module_conf_snd-soc-imx-gsm030x = "blacklist snd-soc-imx-gsm030x"
module_conf_cfg80211 = "options cfg80211 ieee80211_regdom=US"
