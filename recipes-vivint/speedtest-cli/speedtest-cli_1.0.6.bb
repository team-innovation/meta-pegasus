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

SRC_URI = "http://updateseng.vivint.com/innovation/downloads/speedtest-cli-1.0.6.tar.gz \
	   file://python3.patch \
	   file://ping-display.patch"

SRC_URI[md5sum] = "41d78a353ee91d718b44197e5d07f7f6"
SRC_URI[sha256sum] = "b657eadaf3731aeee3173c4b50868e4eee642632a917f8107b85f96df29338ec"

S = "${WORKDIR}/speedtest-cli"

do_install_prepend() {
	install -d ${D}/${libdir}/${PYTHON_DIR}/site-packages/speedtest-cli/
}

do_compile() {
	:
}

do_install() {
	cp -a ${WORKDIR}/speedtest-cli/* ${D}/${libdir}/${PYTHON_DIR}/site-packages/speedtest-cli/
}
