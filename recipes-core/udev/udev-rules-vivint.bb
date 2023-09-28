DESCRIPTION = "udev rules for Vivint NGH"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

PR = "r1"

SRC_URI = " \
	file://local.rules \
	file://99-goodix-touch.rules \
	file://mount.sh \
	file://mount.blacklist \
	file://69-disable-usb-hid.rules \
"

S = "${WORKDIR}"

do_install() {
	install -d ${D}${sysconfdir}/udev/rules.d
	install -m 0644 ${WORKDIR}/99-goodix-touch.rules ${D}${sysconfdir}/udev/rules.d/
	install -m 0644 ${WORKDIR}/69-disable-usb-hid.rules ${D}${sysconfdir}/udev/rules.d/

	if [ -e "${WORKDIR}/local.rules" ]; then
		install -d ${D}${sysconfdir}/udev/rules.d
		if [ -e ${D}${sysconfdir}/udev/rules.d/local.rules ]; then
			rm ${D}${sysconfdir}/udev/rules.d/local.rules
		fi
		install -m 0644 ${WORKDIR}/local.rules ${D}${sysconfdir}/udev/rules.d/local.rules
	fi

	if [ -e "${WORKDIR}/mount.sh" ]; then
		install -d ${D}${sysconfdir}/udev/scripts
		install -m 0755 ${WORKDIR}/mount.sh ${D}${sysconfdir}/udev/scripts/mount.sh
	fi


	if [ -e "${WORKDIR}/mount.blacklist" ]; then
		install -d ${D}${sysconfdir}/udev
		install -m 0644 ${WORKDIR}/mount.blacklist ${D}${sysconfdir}/udev/mount.blacklist
	fi
}
