FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
PR = "7"

SRC_URI += "file://avahi-daemon-hub.conf"
SRC_URI += "file://avahi-daemon-screen.conf"

do_install_append () {
    install -m 0644 ${WORKDIR}/avahi-daemon-hub.conf ${D}${sysconfdir}/avahi
    install -m 0644 ${WORKDIR}/avahi-daemon-screen.conf ${D}${sysconfdir}/avahi
}

FILES_${PN} += "${sysconfdir}/avahi/avahi-daemon-hub.conf"
FILES_${PN} += "${sysconfdir}/avahi/avahi-daemon-screen.conf"

pkg_postinst_${PN} () {
#!/bin/sh
if [ "x$D" != "x" ]; then
        exit 1
fi

if grep -q slimline /proc/device-tree/compatible; then
	rm /etc/avahi/avahi-daemon.conf 
	cp /etc/avahi/avahi-daemon-screen.conf /etc/avahi/avahi-daemon.conf
else
	rm /etc/avahi/avahi-daemon.conf 
	cp /etc/avahi/avahi-daemon-hub.conf /etc/avahi/avahi-daemon.conf
fi

}

