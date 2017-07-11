DESCRIPTION = "U-Boot Script - Boot loader scripts loaded by u-boot for Slimline"
HOMEPAGE = "http://www.vivint.com"
SECTION = "bootloaders"
PRIORITY = "optional"
LICENSE = "Closed"

DEPENDS = "u-boot-mkimage-native"
UBOOT_SCRIPT = "boot.scr"
USB_REFLASH_SCRIPT = "usb-reflash-boot.scr"
SCR_MNT = "/media/bootscript"

COMPATIBLE_MACHINE = "imx6dl-slimline"

SRC_URI = "git://${GIT_SERVER}/uboot-imx.git;protocol=ssh;branch=master"
SRCREV = "${AUTOREV}"

inherit deploy

S = "${WORKDIR}/git"

PACKAGE_ARCH = "${MACHINE_ARCH}"

TARGET_BOARD ?= "Slimline"

PR = "r13"

FILES_${PN} = "/boot"

do_mkimage () {
	uboot-mkimage -A arm -O linux -T script -C none -a 0 -e 0 \
		-n 'slimline bootscript' \
		-d ${S}/board/vivint/mx6dlslimline/bootscript.txt \
		${S}/${UBOOT_SCRIPT}
	uboot-mkimage -A arm -O linux -T script -C none -a 0 -e 0 \
		-n 'slimline bootscript' \
		-d ${S}/board/vivint/mx6dlslimline/usb-reflash-boot.txt \
		${S}/${USB_REFLASH_SCRIPT}
}

addtask mkimage after do_compile before do_install

do_deploy () {
    install -d ${DEPLOYDIR}
    install ${S}/${UBOOT_SCRIPT} \
            ${DEPLOYDIR}/${UBOOT_SCRIPT}-${MACHINE}-${PV}-${PR}
    install ${S}/${USB_REFLASH_SCRIPT} \
            ${DEPLOYDIR}/${USB_REFLASH_SCRIPT}-${MACHINE}-${PV}-${PR}

    cd ${DEPLOYDIR}
    rm -f ${UBOOT_SCRIPT}-${MACHINE}
    rm -f ${USB_REFLASH_SCRIPT}-${MACHINE}
    ln -sf ${UBOOT_SCRIPT}-${MACHINE}-${PV}-${PR} ${UBOOT_SCRIPT}-${MACHINE}
    ln -sf ${USB_REFLASH_SCRIPT}-${MACHINE}-${PV}-${PR} ${USB_REFLASH_SCRIPT}-${MACHINE}
}

addtask deploy after do_install before do_build

do_install () {
	install -d ${D}/boot
	install ${S}/${UBOOT_SCRIPT} ${D}/boot/${UBOOT_SCRIPT}
	install ${S}/${USB_REFLASH_SCRIPT} ${D}/boot/${USB_REFLASH_SCRIPT}
}

do_configure[noexec] = "1"
do_compile[noexec] = "1"
do_populate_sysroot[noexec] = "1"
