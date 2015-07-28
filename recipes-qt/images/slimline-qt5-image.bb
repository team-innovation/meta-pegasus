DESCRIPTION = "Slimline qt5 image."
LICENSE = "MIT"
PR = "r1"
IMAGE_FEATURES += "package-management" 

#touchlink-apps 
#python3-subprocess


IMAGE_INSTALL += "\
	${CORE_IMAGE_BASE_INSTALL} \
	packagegroup-slimline-mfg \
	packagegroup-touchlink-apps \ 
	kernel-image \
	kernel-devicetree \
	u-boot-fw-utils \
	u-boot-slimline \
	packagegroup-slimline-video \
	alsa-utils-aplay \
	python3-distribute \
	python3-misc \
	python3-pyqt5 \
	strace \
	psoc-fw \
	slimline-modules \
	wps-sh \
	fcc-gunk \
"

inherit core-image
