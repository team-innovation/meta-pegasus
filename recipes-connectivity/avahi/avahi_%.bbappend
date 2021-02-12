FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
PR = "7"

SRC_URI += "file://avahi-daemon-hub.conf"
SRC_URI += "file://avahi-daemon-screen.conf"
SRC_URI += "file://vivint-hub.service"

do_install_append () {
    install -d ${D}/${sysconfdir}/avahi/services/
    install -m 0644 ${WORKDIR}/avahi-daemon-hub.conf ${D}${sysconfdir}/avahi
    install -m 0644 ${WORKDIR}/avahi-daemon-screen.conf ${D}${sysconfdir}/avahi
    install -m 0644 ${WORKDIR}/vivint-hub.service ${D}${sysconfdir}/avahi/services
}

FILES_${PN} += "${sysconfdir}/avahi/avahi-daemon-hub.conf"
FILES_${PN} += "${sysconfdir}/avahi/avahi-daemon-screen.conf"
FILES_${PN} += "${sysconfdir}/avahi/vivint-hub.service"

pkg_postinst_ontarget_${PN} () {
#!/bin/sh
if [ "x$D" != "x" ]; then
        exit 1
fi

if [ -e /media/bootscript/serialnumber2.txt ]
then
    serialnumber=$(cat /media/bootscript/serialnumber2.txt)
elif [ -e /media/bootscript/serialnumber.txt ]
then
    serialnumber=$(cat /media/bootscript/serialnumber.txt)
else
    serialnumber="UNKNOWN"
fi

if [ -e /etc/version ]
then
    versionnumber=$(cat /etc/version)
else
    versionnumber="UNKNOWN"
fi

sed -i "s/SERIAL_NUMBER/${serialnumber}/g" /etc/avahi/services/vivint-hub.service
sed -i "s/FIRMWARE_VERSION/${versionnumber}/g" /etc/avahi/services/vivint-hub.service

if grep -q slimline /proc/device-tree/compatible; then
	rm /etc/avahi/avahi-daemon.conf 
	cp /etc/avahi/avahi-daemon-screen.conf /etc/avahi/avahi-daemon.conf
    sed -i "s/Vivint Hub/Vivint Screen/g" /etc/avahi/services/vivint-hub.service
    sed -i "s/_vivint-hub/_vivint-screen/g" /etc/avahi/services/vivint-hub.service
	mv /etc/avahi/services/vivint-hub.service /etc/avahi/services/vivint-screen.service
else
	rm /etc/avahi/avahi-daemon.conf 
	cp /etc/avahi/avahi-daemon-hub.conf /etc/avahi/avahi-daemon.conf
fi

}

