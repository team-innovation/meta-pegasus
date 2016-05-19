require recipes-core/images/core-image-minimal.bb

DESCRIPTION = "Small image to be used with usbboot capable partitioning \
eMMC and populating the rootfs."

IMAGE_INSTALL_append = " \
	packagegroup-sawmill-min \
	util-linux-agetty \
"

IMAGE_INSTALL_remove = " packagegroup-fsl-bluez5-tools"

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
