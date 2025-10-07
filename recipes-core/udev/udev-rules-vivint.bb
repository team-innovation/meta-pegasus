DESCRIPTION = "udev rules for Vivint Pegasus"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

PR = "r5"

SRC_URI = " \
	file://60-vivint.rules \
	file://60-goodix-touch.rules \
	file://mount.sh \
	file://mount.blacklist \
	file://69-disable-usb-hid.rules \
"

S = "${WORKDIR}"

do_install() {
    install -d ${D}${sysconfdir}/udev/rules.d
    install -m 0644 ${WORKDIR}/60-goodix-touch.rules ${D}${sysconfdir}/udev/rules.d/
    install -m 0644 ${WORKDIR}/69-disable-usb-hid.rules ${D}${sysconfdir}/udev/rules.d/

    install -m 0644 ${WORKDIR}/60-vivint.rules ${D}${sysconfdir}/udev/rules.d/

    install -d ${D}${sysconfdir}/udev/scripts
    install -m 0755 ${WORKDIR}/mount.sh ${D}${sysconfdir}/udev/scripts/mount.sh

    install -d ${D}${sysconfdir}/udev
    install -m 0644 ${WORKDIR}/mount.blacklist ${D}${sysconfdir}/udev/mount.blacklist
}
