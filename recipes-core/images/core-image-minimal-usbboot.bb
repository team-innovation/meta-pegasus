require recipes-core/images/core-image-minimal.bb

DESCRIPTION = "Small image to be used with usbboot capable partitioning \
eMMC and populating the rootfs."

# whats the difference between _append and using += ?
IMAGE_INSTALL_append = " kernel-image kernel-devicetree"

# need sfdisk for disk partitioning
IMAGE_INSTALL += "util-linux-sfdisk"

# ext4 formatting
IMAGE_INSTALL += "util-linux-mkfs e2fsprogs-mke2fs"

# agetty for autologin feature absent in busybox getty
IMAGE_INSTALL += "util-linux-agetty"

# slimline-inittab for initial partitioning and populating
# of eMMC
IMAGE_INSTALL += "slimline-initemmc"

# fw_{print,set}env and /etc/fw_env.config
IMAGE_INSTALL += "u-boot-fw-utils"
IMAGE_INSTALL += "u-boot-slimline"

# modify inittab to autologin as root on console with no password

root_autologin() {
	# comment out the fsl etc/init.d/rc_mxc.S line
	# change getty to agetty -a root in the commented one
	# finally uncomment the final one
	sed -i \
		-e '/^mxc/s/mxc/#mxc/' \
		-e '/^#mxc/s/getty/agetty -a root/'  \
		-e '/^#mxc.*agetty/s/^#mxc/mxc/' \
			${IMAGE_ROOTFS}/etc/inittab
}

ROOTFS_POSTPROCESS_COMMAND_prepend = "root_autologin; "
