DESCRIPTION = "Sly qt5 image."
LICENSE = "MIT"
PR = "r11"
IMAGE_FEATURES += "package-management" 

IMAGE_INSTALL_append = "\
	${CORE_IMAGE_BASE_INSTALL} \
	packagegroup-sawmill-common \
	packagegroup-touchlink-apps-sly \
	packagegroup-sawmill-sly \
	packagegroup-sawmill-video \
	alsa-utils-aplay \
	pulseaudio-meta-sly \
	audio-sh \
	mtd-utils \
	packagegroup-camera-firmware \
	packagegroup-ppp-config-modem \
	packagegroup-zwave-config \
"

inherit core-image

license_create_manifest_append() {
# We don't want some of the files installed by the license helper scripts
    if [ -e ${IMAGE_ROOTFS}/usr/share/common-licenses/ ]; then
        rm -f ${IMAGE_ROOTFS}/usr/share/common-licenses/license.manifest
        rm -f ${IMAGE_ROOTFS}/usr/share/common-licenses/generic_*
        for d in $(find ${IMAGE_ROOTFS}/usr/share/common-licenses -type d); do
           rm -fd "$d"/generic_* 
	   rm -fd "$d"/*.h
	   if [ ! -d "$d"/*.d ]; then
		   rm -f "$d"/*.d
	   fi
	   rm -fd "$d"/*.am
	   rm -fd "$d"/*.c
	   rm -fd "$d"/*.py
	   rm -fd "$d"/*.in
	   rm -fd "$d"/*.pl
           rmdir --ignore-fail-on-non-empty "$d"
        done

    fi
}

IMAGE_FSTYPES_append = " emmc"
