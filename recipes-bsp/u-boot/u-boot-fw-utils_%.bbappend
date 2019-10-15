FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

GIT_UBOOT_SERVER = "source.vivint.com:7999/em"
GIT_UBOOT_BRANCH = "wallsly-next"
GIT_UBOOT_PROTOCOL = "ssh"
SRC_URI = "git://${GIT_UBOOT_SERVER}/uboot-imx;protocol=${GIT_UBOOT_PROTOCOL};branch=${GIT_UBOOT_BRANCH}"
SRCREV = "${AUTOREV}"
PR = "3"
PV = "v2016.03+git${SRCPV}"
UBOOT_MACHINE = "mx6dlwallsly_defconfig"

SRC_URI += "file://fw_env.config"
SRC_URI += "file://default-gcc.patch"
SRC_URI += "file://fix-build-for-gcc7.patch"

do_install_append() {
    install -m 0644 ${WORKDIR}/fw_env.config ${D}${sysconfdir}/fw_env.config
}
