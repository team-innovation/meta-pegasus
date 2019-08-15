GIT_KERNEL_BRANCH = "feature/4.14.98_2.0.0_ga"
GIT_KERNEL_SERVER = "${GIT_SERVER}"
GIT_KERNEL_PROTOCOL = "ssh"
GIT_KERNEL_REV = "${AUTOREV}"
SRCREV = "${GIT_KERNEL_REV}"
KERNEL_SRC = "git://${GIT_KERNEL_SERVER}/linux-imx;protocol=${GIT_KERNEL_PROTOCOL}"
SRC_URI = "${KERNEL_SRC};branch=${GIT_KERNEL_BRANCH}"

LOCALVERSION = "-2.0.0-ga+yocto"
COMPATIBLE_MACHINE = "(mx6|mx7|mx8)"

S = "${WORKDIR}/git"

do_merge_default_config() {

    if [ -f ${S}/arch/${ARCH}/configs/${KERNEL_DEFCONFIG} ]; then
	# create config with make config
	oe_runmake  -C ${S} O=${KBUILD_OUTPUT} ${KERNEL_DEFCONFIG}
	cp ${KBUILD_OUTPUT}/.config ${WORKDIR}/defconfig
    fi

}

addtask merge_default_config before do_preconfigure after do_patch
