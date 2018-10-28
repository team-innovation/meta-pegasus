# nfc-util build recipe
# Copyright (C) 2018, Vivint Innovation
# 

DESCRIPTION = "nfc-util interface to CR95HF"
HOMEPAGE = "https://source.vivint.com/projects/EM/repos/nfc-util/browse"
LICENSE = "CLOSED"
SECTION = "devel"
PR = "r1.0"

SRCREV = "6bcfea84921436ddf614e98a5b9d90e3ca152bd3"
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
