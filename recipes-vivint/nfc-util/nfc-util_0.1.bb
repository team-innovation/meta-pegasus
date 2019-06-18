# nfc-util build recipe
# Copyright (C) 2018, Vivint Innovation
# 

DESCRIPTION = "nfc-util interface to CR95HF"
HOMEPAGE = "https://source.vivint.com/projects/EM/repos/nfc-util/browse"
LICENSE = "CLOSED"
SECTION = "devel"
PR = "r1.4"

SRCREV = "75ebb39bbe6b42da3bbee81e1e7f5a95eb4f9262"
MODULE = "nfc-util"
SRC_URI = "git://${GIT_SERVER}/${MODULE};protocol=ssh"

INHIBIT_PACKAGE_DEBUG_SPLIT = '1'

S = "${WORKDIR}/git"
TARGET_CC_ARCH += "${LDFLAGS}"

do_compile() {
	make -C src
}

do_install() {
	mkdir -p ${D}${bindir}
	install -m 0755 ${S}/src/nfc-util ${D}${bindir}/
}


PACKAGES = "${PN}"
FILES_${PN} = "${bindir}/nfc-util"
FILES_${PN}-dbg = "/usr/src/debug ${bindir}/.debug"
