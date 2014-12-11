DESCRIPTION = "Slimeline qt4e image."
LICENSE = "MIT"
PR = "r1"

IMAGE_INSTALL += "\
	${CORE_IMAGE_BASE_INSTALL} \
	packagegroup-core-qt4e \
	packagegroup-slimline-mfg \
	kernel-image \
	kernel-devicetree \
	u-boot-fw-utils \
	u-boot-slimline \
"

inherit core-image
