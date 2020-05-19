SUMMARY = "Command line interface for testing internet bandwidth"
DESCRIPTION = "Command line interface for testing internet bandwidth using speedtest.net. \
This is used in the skycontrol panel to determine camera streaming parameters."
HOMEPAGE = "https://github.com/sivel/speedtest-cli"
AUTHOR = "Matt Martz"
SECTION = "base"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"
PR = "r1"

DEPENDS += "python3 python3-native"

inherit distutils3 python-dir

SRC_URI = "http://updateseng.vivint.com/innovation/downloads/speedtest-cli-0.3.2.tar.gz \
	   file://python3.patch \
	   file://ping-display.patch"
SRC_URI[md5sum] = "a4806d01d5573e427d3bc1fdec85ccc0"
SRC_URI[sha256sum] = "7b4930d1d78ca51a9f95fd55706eb317790ee1545b8316ec3207f3d82dce90bf"

S = "${WORKDIR}/speedtest-cli"

do_install_prepend() {
	install -d ${D}/${libdir}/${PYTHON_DIR}/site-packages/speedtest-cli/
}

do_configure[noexec] = "1"
do_compile[noexec] = "1"

do_install() {
	cp -a ${WORKDIR}/speedtest-cli/* ${D}/${libdir}/${PYTHON_DIR}/site-packages/speedtest-cli/
}
