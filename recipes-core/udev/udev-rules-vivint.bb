DESCRIPTION = "udev rules for Vivint NGH"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

PR = "r5"

SRC_URI = " \
	file://60-vivint.rules \
	file://60-goodix-touch.rules \
	file://60-goodix-touch.rules_yellowstone \
	file://mount.sh \
	file://mount.blacklist \
	file://69-disable-usb-hid.rules \
"

S = "${WORKDIR}"

python __anonymous() {
    machine = d.getVar('MACHINE', True)

    if 'yellowstone' in machine:
        platform = 'yellowstone'
    else:
        platform = ''

    d.setVar('BUILD_PLATFORM', platform)
}

do_install() {
	install -d ${D}${sysconfdir}/udev/rules.d
    if [ ${BUILD_PLATFORM} == 'yellowstone' ] ; then
        install -m 0644 ${WORKDIR}/60-goodix-touch.rules_yellowstone ${D}${sysconfdir}/udev/rules.d/60-goodix-touch.rules
    else
        install -m 0644 ${WORKDIR}/60-goodix-touch.rules ${D}${sysconfdir}/udev/rules.d/
    fi
	install -m 0644 ${WORKDIR}/69-disable-usb-hid.rules ${D}${sysconfdir}/udev/rules.d/

    install -m 0644 ${WORKDIR}/60-vivint.rules ${D}${sysconfdir}/udev/rules.d/

    install -d ${D}${sysconfdir}/udev/scripts
    install -m 0755 ${WORKDIR}/mount.sh ${D}${sysconfdir}/udev/scripts/mount.sh

    install -d ${D}${sysconfdir}/udev
    install -m 0644 ${WORKDIR}/mount.blacklist ${D}${sysconfdir}/udev/mount.blacklist
}
