SUMMARY = "Z/IP Gateway"
HOMEPAGE = "http://zts.sigmadesigns.com"
SECTION = "network"
LICENSE = "CLOSED"
LIC_FILES_CHKSUM = "file://LICENSE;md5=c5572362acb437d9c5e365a4198a459b"

DEPENDS = "python-native libusb libxml2-native libxslt-native openssl flex json-c"
RDEPENDS_${PN} = "bridge-utils"

PR = "r2"
PV = "7.11.02+git${SRCPV}"

SRCREV = "c2e2264693d910c372c678bb31773e25e6959a3d"
SRCBRANCH = "v71102_sumo"

GIT_ZGATE_SERVER ?= "${GIT_SERVER}"
GIT_ZGATE_PROTOCOL ?= "ssh"

SRC_URI = "git://${GIT_ZGATE_SERVER}/zware_controller_sdk;protocol=${GIT_ZGATE_PROTOCOL};branch=${SRCBRANCH} \
           file://zwaved \
           "

S = "${WORKDIR}/git/zipgateway-7.11.02-Source/usr/local"

inherit pkgconfig cmake python3-dir python3native update-rc.d

# Create runlevel links
INITSCRIPT_NAME = "zwaved"
INITSCRIPT_PARAMS = "start 30 5 ."

EXTRA_OECMAKE = " \
    -DCMAKE_INSTALL_PREFIX=/usr/local \
    -DSKIP_TESTING=TRUE \
    -DDISABLE_MOCK=TRUE \
"

do_package_qa() {
    echo "Skipping QA ..."
}

do_install_append() {
    install -d ${D}${sysconfdir}/init.d
    install -m 0755 ${WORKDIR}/zwaved ${D}${sysconfdir}/init.d

    rm -f ${D}/${prefix}/local/etc/zipgateway_node_identify_rpi3b+_led.sh
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
FILES_${PN} += "${prefix}/local/bin/zgw_restore"
FILES_${PN} += "${prefix}/local/bin/zw_nvm_converter"
FILES_${PN} += "${prefix}/local/bin/zw_programmer"

FILES_${PN}-dbg += "${prefix}/local/bin/.debug"
FILES_${PN}-dbg += "${prefix}/local/sbin/.debug"

RPROVIDES_${PN} = "zipgateway"

