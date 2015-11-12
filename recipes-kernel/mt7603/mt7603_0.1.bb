DESCRIPTION = "MediaTek 7603 kernel module."
LICENSE = "CLOSED"

inherit module-base

PR = "r2"
PV = "0.1"

SRC_URI = " \
	file://MT7603_STA-PCI-V3.0.1.1.tar.bz2 \
	file://0001-patch-for-yocto-build.patch \
"

S = "${WORKDIR}/rlt_wifi"

do_compile() {
	LDFLAGS="" make V=1 \
	CROSS_COMPILE=${CROSS_COMPILE} \
	LINUX_SRC=${STAGING_KERNEL_DIR} \
}

KMODDIR = "/lib/modules/${KERNEL_VERSION}/kernel/drivers/net/wireless/mtk"
DATDIR = "/etc/Wireless/etc/Wireless/RT2860STA/"

do_install() {
	make DAT_PATH=${D}/${DATDIR} LINUX_SRC_MODULE=${D}/${KMODDIR} install
}

FILES_${kernel-module-PN} = "\
	${KMODDIR} \
	${DATDIR} \
"

PACKAGE_ARCH = "${MACHINE_ARCH}"

PKG_${PN} = "kernel-module-${PN}"
