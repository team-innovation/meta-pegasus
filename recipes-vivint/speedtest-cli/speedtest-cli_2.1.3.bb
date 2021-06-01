SMMARY = "Command line interface for testing internet bandwidth"
DESCRIPTION = "Command line interface for testing internet bandwidth using speedtest.net. \
This is used in the skycontrol panel to determine camera streaming parameters."
HOMEPAGE = "https://github.com/sivel/speedtest-cli"
AUTHOR = "Matt Martz"
SECTION = "base"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"
PR = "r1"

DEPENDS += "python3 python3-native"

inherit distutils3 python3-dir

SRC_URI = "http://updateseng.vivint.com/innovation/downloads/speedtest-cli-${PV}.tar.gz \
	   file://ping-display-${PV}.patch"

SRC_URI[md5sum] = "4bd1cf455e210ce47be1a3290988160b"
SRC_URI[sha256sum] = "5e2773233cedb5fa3d8120eb7f97bcc4974b5221b254d33ff16e2f1d413d90f0"

S = "${WORKDIR}/speedtest-cli-${PV}"

do_install_prepend() {
	install -d ${D}/${libdir}/${PYTHON_DIR}/site-packages/speedtest-cli/
}

do_configure[noexec] = "1"
do_compile[noexec] = "1"

do_install() {
	cp -a ${WORKDIR}/speedtest-cli-${PV}/* ${D}/${libdir}/${PYTHON_DIR}/site-packages/speedtest-cli/
}
