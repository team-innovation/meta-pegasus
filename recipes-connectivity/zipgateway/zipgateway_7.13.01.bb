SUMMARY = "Z/IP Gateway"
HOMEPAGE = "http://zts.sigmadesigns.com"
SECTION = "network"
LICENSE = "CLOSED"
LIC_FILES_CHKSUM = "file://LICENSE;md5=c5572362acb437d9c5e365a4198a459b"

DEPENDS = "python-native libusb flex-native bison-native json-c openssl libxslt-native libusb-native json-c-native"
RDEPENDS_${PN} = "bridge-utils openssl bash"

PR = "r2"
PV = "7.13.01+git${SRCPV}"

SRCREV = "ace817ccd812cc491b22a260c1f3691621299aa0"
SRCBRANCH = "v7.13.1"

GIT_ZGATE_SERVER ?= "${GIT_SERVER}"
GIT_ZGATE_PROTOCOL ?= "ssh"

SRC_URI = "git://${GIT_ZGATE_SERVER}/zware_controller_sdk;protocol=${GIT_ZGATE_PROTOCOL};branch=${SRCBRANCH} \
           file://zwaved \
           file://zwaved.service \
           "

S = "${WORKDIR}/git/zipgateway-7.13.01-Source/usr/local"

inherit pkgconfig cmake python-dir pythonnative update-rc.d

INITSCRIPT_NAME = "zwaved"
INITSCRIPT_PARAMS = "start 30 5 ."

EXTRA_OECMAKE = " \
    -DCMAKE_INSTALL_PREFIX=${prefix}/local \
    -DSKIP_TESTING=TRUE \
    -DDISABLE_MOCK=TRUE \
    -DDISABLE_DTLS=TRUE \
"

do_install_append() {
    install -d ${D}${sysconfdir}/init.d
    install -m 0755 ${WORKDIR}/zwaved ${D}${sysconfdir}/init.d

    rm -f ${D}/${prefix}/local/etc/zipgateway_node_identify_rpi3b+_led.sh

    # systemd services
    install -d ${D}${systemd_unitdir}/system-preset
    echo "enable zwaved.service" > ${D}${systemd_unitdir}/system-preset/98-zwaved.preset
    install -d ${D}${systemd_unitdir}/system
    install -m 0644 ${WORKDIR}/zwaved.service ${D}${systemd_unitdir}/system
}

do_install_append_class-nativesdk() {
	mv ${D}/etc/radvd.conf ${D}${sysconfdir}
	mv ${D}/etc/init.d/zipgateway ${D}${sysconfdir}/init.d/
	rmdir ${D}/etc/init.d
	rmdir ${D}/etc
}


# TODO: remove this one we no longer using init.d

pkg_postinst_ontarget_${PN} () {
#!/bin/sh -e
# Post install to make sure systemd is setup
#
        mkdir -p /media/extra/log/error
        if type systemctl >/dev/null 2>/dev/null; then
                OPTS=""

                if [ "enable" = "enable" ]; then
                        for service in zwaved.service; do
                                case "${service}" in
                                *@*)
                                        systemctl ${OPTS} enable "${service}"
                                        ;;
                                esac
                        done
                fi

                systemctl daemon-reload
                systemctl preset zwaved.service

                if [ "enable" = "enable" ]; then
                        systemctl --no-block restart zwaved.service
                fi
        fi

	# update /usr/local/etc/zipgateway.cfg to use ttymxc3 for Nene
        if grep -q vivint,nene "/proc/device-tree/compatible" ; then
                sed -i 's/ttymxc2/ttymxc3/g' /usr/local/etc/zipgateway.cfg
        fi
}

pkg_prerm_ontarget_${PN} () {
#!/bin/sh -e
        if type systemctl >/dev/null 2>/dev/null; then
                systemctl stop zwaved.service
                systemctl disable zwaved.service
        fi
}


FILES_${PN} += "${prefix}/local/sbin/zipgateway"
FILES_${PN} += "${prefix}/local/sbin/udprelay"
FILES_${PN} += "${prefix}/local/etc/zipgateway.cfg"
FILES_${PN} += "${prefix}/local/etc/zipgateway_provisioning_list.cfg"
FILES_${PN} += "${prefix}/local/etc/zipgateway_node_identify_generic.sh"
FILES_${PN} += "${prefix}/local/etc/zipgateway.tun"
FILES_${PN} += "${prefix}/local/etc/zipgateway.fin"
FILES_${PN} += "${prefix}/local/etc/ZIPR.x509_1024.pem"
FILES_${PN} += "${prefix}/local/etc/ZIPR.key_1024.pem"
FILES_${PN} += "${prefix}/local/etc/Portal.ca_x509.pem"
FILES_${PN} += "${sysconfdir}/init.d/zwaved"
FILES_${PN} += "${sysconfdir}/rcS.d/*zwaved"
FILES_${PN} += "${prefix}/local/man/man3/zipgateway.3"
FILES_${PN} += "${prefix}/local/bin/zgw_convert_eeprom"
FILES_${PN} += "${prefix}/local/bin/zgw_import.sh"
FILES_${PN} += "${prefix}/local/bin/zgw_backup.sh"
FILES_${PN} += "${prefix}/local/bin/zgw_recover.sh"
FILES_${PN} += "${prefix}/local/bin/zgw_restore"
FILES_${PN} += "${prefix}/local/bin/zw_nvm_converter"
FILES_${PN} += "${prefix}/local/bin/zw_programmer"
FILES_${PN} += "${systemd_unitdir}/system/zwaved.service"
FILES_${PN} += "${systemd_unitdir}/system-preset/98-zwaved.preset"

FILES_${PN}-dbg += "${prefix}/local/bin/.debug"
FILES_${PN}-dbg += "${prefix}/local/sbin/.debug"

RPROVIDES_${PN} = "zipgateway"

BBCLASSEXTEND = "native nativesdk"
