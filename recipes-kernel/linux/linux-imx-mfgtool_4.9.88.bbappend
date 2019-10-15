FILESEXTRAPATHS_prepend := "${THISDIR}/linux-fslc-imx:"
FILESEXTRAPATHS_prepend := "${THISDIR}/imx:"

GIT_KERNEL_SERVER = "source.vivint.com:7999/em"
GIT_KERNEL_PROTOCOL = "ssh"
SRCREV = "${AUTOREV}"
GIT_KERNEL_BRANCH = "rocko"
SRC_URI = "git://${GIT_KERNEL_SERVER}/linux-imx;protocol=${GIT_KERNEL_PROTOCOL};branch=${GIT_KERNEL_BRANCH}\
           file://defconfig \
           "
           
PR = "r23"
PV = "4.9.88+git${SRCPV}"

do_deploy_prepend() {
    install -d ${DEPLOY_DIR_IMAGE}
    install  arch/arm/boot/zImage ${DEPLOY_DIR_IMAGE}/zImage_mfgtool
}

