# whats the difference between _append and using += ?
# kernel and devicetree:
#	uImage and dtb in /boot where u-boot expects them
# u-boot-fw-utils:
#	fw_{print,set}env
# u-boot-slimline:
#	/etc/fw_env.config

IMAGE_INSTALL_append = " kernel-image kernel-devicetree u-boot-fw-utils u-boot-slimline"
