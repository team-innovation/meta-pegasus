DESCRIPTION = "Sly qt5 image."
LICENSE = "MIT"
PR = "r9"
IMAGE_FEATURES += "package-management" 

IMAGE_INSTALL_append = "\
	${CORE_IMAGE_BASE_INSTALL} \
	packagegroup-sawmill-common \
	packagegroup-touchlink-apps-sly \
	packagegroup-sawmill-sly \
	packagegroup-sawmill-video \
	alsa-utils-aplay \
	pulseaudio-meta-sly \
	python3-pyqt5 \
	audio-sh \
	mtd-utils \
	packagegroup-camera-firmware \
	packagegroup-ppp-config-modem \
	packagegroup-zwave-config \
	bsp-rt288x-hg \
"

inherit core-image

IMAGE_FSTYPES_append = " emmc"
