DESCRIPTION = "Boot script for launching images with U-Boot distro boot"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

DEPENDS = "u-boot-mkimage-native"

inherit deploy

SRC_URI = "file://boot.cmd"

do_mkimage () {
    mkimage -A arm -O linux -T script -C none -a 0 -e 0 \
              -n "boot script" -d ${WORKDIR}/boot.cmd ${S}/boot.scr
}

addtask mkimage after do_compile before do_install

do_configure[noexec] = "1"
do_compile[noexec] = "1"

do_install () {
    install -D -m 644 ${S}/boot.scr ${D}/boot/boot.scr
}

do_deploy () {
    install -D -m 644 ${D}/boot/boot.scr \
        ${DEPLOYDIR}/boot.scr-${MACHINE}-${PV}-${PR}

    cd ${DEPLOYDIR}
    rm -f boot.scr-${MACHINE}
    ln -sf boot.scr-${MACHINE}-${PV}-${PR} boot.scr-${MACHINE}
}

addtask deploy after do_install before do_build

PROVIDES += "u-boot-default-script"

FILES:${PN} = "/boot"
