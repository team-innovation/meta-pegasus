FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
PR = "3"

SRC_URI += "file://hostapd.conf"

INITSCRIPT_PARAMS = "stop 30 2 3 4 5 . " 

do_install_append () {
    install -m 0644 ${WORKDIR}/hostapd.conf ${D}${sysconfdir}
}

pkg_postinst_${PN} () {
#!/bin/sh -e
# Post install to make sure we have the correct setup for updated
#

if [ x"$D" = "x" ]; then
    read macaddr </sys/class/net/wlan0/address
    macaddrup="$(echo $macaddr | tr '[:lower:]' '[:upper:]')"
    sed -i "s/ssid=.*/ssid=VIVINT_GLANCE_(${macaddrup:9})/" ${sysconfdir}/hostapd.conf
fi
}
