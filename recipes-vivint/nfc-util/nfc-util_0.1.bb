# nfc-util build recipe
# Copyright (C) 2018, Vivint Innovation
# 

DESCRIPTION = "nfc-util interface to CR95HF"
HOMEPAGE = "https://source.vivint.com/projects/EM/repos/nfc-util/browse"
LICENSE = "CLOSED"
SECTION = "devel"
PR = "r1.1"

SRCREV = "c26df53a92e3d10b195ecd3470023313923897ba"
MODULE = "nfc-util"
SRC_URI = "git://${GIT_SERVER}/${MODULE};protocol=ssh"

S = "${WORKDIR}/git"
TARGET_CC_ARCH += "${LDFLAGS}"

do_compile() {
	make -C src
}

do_install() {
	mkdir -p ${D}${bindir}
	install -m 0755 ${S}/src/nfc-util ${D}${bindir}/
}


PACKAGES = "nfc-util"
FILES_${PN} = "${bindir}/nfc-util"
FILES_${PN}-dbg = "/usr/src/debug ${bindir}/.debug"
