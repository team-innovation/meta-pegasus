DESCRIPTION = "MediaTek 7663 wifi firmware"
LICENSE = "CLOSED"

PR = "r12"

SRC_URI = " \
	file://MT7663E_EEPROM1.bin \
	file://wireless \
	file://mtk-net-ctrl \
"


do_configure[noexec] = "1"
do_compile[noexec] = "1"
do_package_qa[noexec] = "1"

do_install() {
	install -d ${D}/usr/local/bin/
	install -d ${D}/${sysconfdir}/wireless/
	install -d ${D}/etc_ro/wlan
 	install -m 644 ${WORKDIR}/MT7663E_EEPROM1.bin ${D}/etc_ro/wlan
	cp -a ${WORKDIR}/wireless/* ${D}/${sysconfdir}/wireless/
	install -m 755 ${WORKDIR}/mtk-net-ctrl ${D}/usr/local/bin/
}

FILES_${PN} = "\
	${sysconfdir}/wireless \
	/etc_ro/wlan	\
	/usr/local/bin \
"

pkg_postinst_ontarget_${PN} () {
#!/bin/sh
	network_conf_dir="/media/extra/conf/network"
	network_profile_etc="/etc/wireless/mediatek/mt7663/mt7663.1.dat"
        network_profile="/media/extra/conf/network/mt7663.1.dat"

	test -f $network_profile && {
		cp $network_profile $network_profile_etc
	}

	exit 0
}
