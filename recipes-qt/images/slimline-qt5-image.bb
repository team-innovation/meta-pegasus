DESCRIPTION = "Slimline qt5 image."
LICENSE = "MIT"
PR = "r1"
IMAGE_FEATURES += "package-management" 

IMAGE_INSTALL += "\
	${CORE_IMAGE_BASE_INSTALL} \
	packagegroup-slimline-mfg \
	packagegroup-touchlink-apps \ 
	packagegroup-slimline-video \
	kernel-image \
	kernel-devicetree \
	u-boot-fw-utils \
	u-boot-slimline \
	alsa-utils-aplay \
	python3-distribute \
	python3-misc \
	python3-subprocess \
	python3-pyqt5 \
	strace \
	psoc-fw \
	slimline-modules \
	wps-sh \
	fcc-gunk \
"

inherit core-image
