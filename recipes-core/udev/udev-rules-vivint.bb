DESCRIPTION = "udev rules for Vivint NGH"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = " \
	file://local.rules \
	file://99-goodix-touch.rules \
"

S = "${WORKDIR}"

do_install() {
	install -d ${D}${sysconfdir}/udev/rules.d
	install -m 0644 ${WORKDIR}/99-goodix-touch.rules ${D}${sysconfdir}/udev/rules.d/

	if [ -e "${WORKDIR}/local.rules" ]; then
		install -d ${D}${sysconfdir}/udev/rules.d
		if [ -e ${D}${sysconfdir}/udev/rules.d/local.rules ]; then
			rm ${D}${sysconfdir}/udev/rules.d/local.rules
		fi
		install -m 0644 ${WORKDIR}/local.rules ${D}${sysconfdir}/udev/rules.d/local.rules
	fi
}
