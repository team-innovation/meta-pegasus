SUMMARY = "Initialize eMMC on first boot"
DESCRIPTION = "Partitions and populates eMMC when usb booting"
SECTION = "base"
LICENSE = "CLOSED"

SRC_URI = "file://sprouter-initemmc.sh \
	   file://dot.profile \
"

FILES_${PN} += "/home/root/.profile"

do_install () {
	install -d ${D}/usr/bin/ 
	install -m 0755 ${WORKDIR}/sprouter-initemmc.sh ${D}/usr/bin/sprouter-initemmc

	install -d ${D}/home/root/ 
	install -m 0755 ${WORKDIR}/dot.profile ${D}/home/root/.profile
}

PACKAGE_ARCH = "${MACHINE_ARCH}"
