SUMMARY = "Networking init scripts and configuration files to support wps"
DESCRIPTION = "This package provides high level tools to connect wlan0 network interfaces"
HOMEPAGE = "http://git.vivint.com"
SECTION = "base"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://${WORKDIR}/COPYING;md5=be94729c3d0e226497bf9ba8c384e96f"
PR = "r5"

SRC_URI = "file://network_func \
           file://udhcpc_wlan0 \
           file://COPYING \
           file://wps.sh \
           file://stop_wps.sh"

do_install () {
	install -d ${D}${sysconfdir}/init.d ${D}${sysconfdir}/network ${D}/usr/local/bin
	install -m 0644 ${WORKDIR}/network_func ${D}${sysconfdir}/network
	install -m 0755 ${WORKDIR}/udhcpc_wlan0 ${D}${sysconfdir}/init.d
	install -m 0755 ${WORKDIR}/wps.sh ${D}/usr/local/bin

	# Creat run level links
	update-rc.d -r ${D} udhcpc_wlan0 start 41 3 4 5 . stop 41 6 .
}

FILES_${PN} += "/usr/local/bin/*"

pkg_postinst_${PN} () {
#!/bin/sh

#make sure we have network wireless.conf and wpa_supplicant_wireless.conf
# symlink
media_extra_conf_network_dir="/media/extra/conf/network"
etc_wireless_conf="/etc/network/wireless.conf"
media_extra_wpa_supplicant_conf="$media_extra_conf_network_dir/wpa_supplicant_wireless.conf"
etc_wpa_supplicant_conf="/etc/network/wpa_supplicant_wireless.conf"

mkdir -p $media_extra_conf_network_dir

if [ ! -h $etc_wireless_conf ]
then
   rm -f $etc_wireless_conf
   ln -sf $media_extra_conf_network_dir $etc_wireless_conf
fi

if [ ! -h $etc_wpa_supplicant_conf ]
then
   rm -f $etc_wpa_supplicant_conf
   ln -sf $media_extra_wpa_supplicant_conf $etc_wpa_supplicant_conf
fi

exit 0
}
