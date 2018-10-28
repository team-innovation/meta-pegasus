# nfc-util build recipe
# Copyright (C) 2018, Vivint Innovation
# 

DESCRIPTION = "nfc-util interface to CR95HF"
HOMEPAGE = "https://source.vivint.com/projects/EM/repos/nfc-util/browse"
LICENSE = "CLOSED"
SECTION = "devel"
PR = "r1.0"

SRCREV = "7f03e22fb75d74571496776cbeabc34cb66d7bae"
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
