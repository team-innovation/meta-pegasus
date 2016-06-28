DESCRIPTION = "Sly qt5 image."
LICENSE = "MIT"
PR = "r8"
IMAGE_FEATURES += "package-management" 

IMAGE_INSTALL_append = "\
	${CORE_IMAGE_BASE_INSTALL} \
	packagegroup-sawmill-common \
	packagegroup-touchlink-apps-sly \
	packagegroup-sawmill-sly \
	packagegroup-sawmill-video \
	alsa-utils-aplay \
	pulseaudio-meta \
	python3-pyqt5 \
	audio-sh \
	mtd-utils \
	packagegroup-ppp-config-modem \
	packagegroup-zwave-config \
	bsp-rt288x-hg \
	touchlink-alpha-firmware-cs6022-ov4689 \
	touchlink-vivotek-firmware-db8332w \
	touchlink-vivotek-firmware-db8331w \
	touchlink-vivotek-firmware-721w \	
"

inherit core-image

IMAGE_FSTYPES_append = " emmc"
