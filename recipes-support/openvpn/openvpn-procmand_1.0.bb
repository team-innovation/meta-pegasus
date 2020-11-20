SUMMARY = "Vivint procmand startup file for openvpn"
HOMEPAGE = "http://vivint.com"
SECTION = "console/network"
LICENSE = "CLOSED"

PR = "r1"


SRC_URI = "file://procman.d"

do_configure[noexec] = "1"
do_compile[noexec] = "1"
do_package_qa[noexec] = "1"

do_install() {
    install -d ${D}/${sysconfdir}/procman.d
    install -m 755 ${WORKDIR}/procman.d/* ${D}/${sysconfdir}/procman.d

}

FILES_${PN} = "${sysconfdir}/procman.d"
