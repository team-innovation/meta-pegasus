DESCRIPTION = "udev rules for sawmill"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=4d92cd373abda3937c2bc47fbc49d690"

SRC_URI = " file://11-media-by-label-auto-mount.rules"

do_install () {
	install -d ${D}${sysconfdir}/udev/rules.d
	install -m 0644 ${WORKDIR}/11-media-by-label-auto-mount.rules ${D}${sysconfdir}/udev/rules.d/
}
