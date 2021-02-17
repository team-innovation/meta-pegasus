DESCRIPTION = "Slimline qt5 image."
LICENSE = "MIT"
PR = "r9"
IMAGE_FEATURES += "package-management" 

IMAGE_INSTALL_append = "\
	${CORE_IMAGE_BASE_INSTALL} \
	packagegroup-sawmill-common \
	packagegroup-touchlink-apps-slimline \
	packagegroup-sawmill-slimline \
	packagegroup-sawmill-video \
	alsa-utils-aplay \
	pulseaudio-meta-slimline \
	audio-sh \
	mtd-utils \
"

inherit core-image

license_create_manifest_append() {
# We don't want some of the files installed by the license helper scripts
    if [ -e ${IMAGE_ROOTFS}/usr/share/common-licenses/ ]; then
        rm -f ${IMAGE_ROOTFS}/usr/share/common-licenses/license.manifest
        rm -f ${IMAGE_ROOTFS}/usr/share/common-licenses/generic_*
        for d in $(find ${IMAGE_ROOTFS}/usr/share/common-licenses -type d); do
           rm -f "$d"/generic_* 
	   rm -f "$d"/*.h
	   if [ ! -d "$d"/*.d ]; then
		   rm -f "$d"/*.d
	   fi
	   rm -f "$d"/*.am
	   rm -f "$d"/*.c
	   rm -f "$d"/*.py
	   rm -f "$d"/*.in
	   rm -f "$d"/*.pl
           rmdir --ignore-fail-on-non-empty "$d"
        done

    fi
}


IMAGE_FSTYPES_append = " emmc"
