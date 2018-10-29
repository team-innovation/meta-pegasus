SUMMARY = "Z/IP Gateway"
HOMEPAGE = "http://zts.sigmadesigns.com"
SECTION = "network"
LICENSE = "CLOSED"
LIC_FILES_CHKSUM = "file://LICENSE;md5=c5572362acb437d9c5e365a4198a459b"

DEPENDS = "python-native libusb openssl flex"
RDEPENDS_${PN} = "bridge-utils"

PR = "r1"
PV = "2.81+git${SRCPV}"

SRCREV = "${AUTOREV}"
SRCBRANCH = "master"

GIT_APPS_SERVER ?= "${GIT_SERVER}"
GIT_APPS_PROTOCOL ?= "ssh"

SRC_URI = "git://${GIT_APPS_SERVER}/zware_controller_sdk;protocol=${GIT_APPS_PROTOCOL};branch=${SRCBRANCH} \
           file://zwaved \
           "

S = "${WORKDIR}/git/zipgateway-2.81.0-Source/usr/local"

inherit pkgconfig cmake python-dir pythonnative

do_package_qa() {
    echo "Skipping QA ..."
}

do_install_append() {
    install -d ${D}${sysconfdir}/init.d
    install -m 0755 ${WORKDIR}/zwaved ${D}${sysconfdir}/init.d

    # Create runlevel links
    update-rc.d -r ${D} zwaved start 30 5 .
}

FILES_${PN} += "${prefix}/local/sbin/zipgateway"
FILES_${PN} += "${prefix}/local/sbin/udprelay"
FILES_${PN} += "${prefix}/local/etc/zipgateway.cfg"
FILES_${PN} += "${prefix}/local/etc/zipgateway.tun"
FILES_${PN} += "${prefix}/local/etc/zipgateway.fin"
FILES_${PN} += "${prefix}/local/etc/ZIPR.x509_1024.pem"
FILES_${PN} += "${prefix}/local/etc/ZIPR.key_1024.pem"
FILES_${PN} += "${prefix}/local/etc/Portal.ca_x509.pem"
FILES_${PN} += "${sysconfdir}/init.d/zwaved"
FILES_${PN} += "${sysconfdir}/rcS.d/*zwaved"
FILES_${PN} += "${prefix}/local/man/man3/zipgateway.3"
FILES_${PN} += "${prefix}/local/sbin/zgw_convert_eeprom"
FILES_${PN} += "${prefix}/local/sbin/.debug/*"

RPROVIDES_${PN} = "zipgateway"

