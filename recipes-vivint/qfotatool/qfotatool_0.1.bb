SUMMARY = "Quectel FOTA Tool"
DESCRIPTION = "Tool for performing local FOTA firmware updates"
SECTION = "base"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://DTool.c;md5=d7f4db8ca2427e38c83206cbb3ea3bbb"
PR = "r1"

SRC_URI = "file://DTool.c"
PACKAGE_ARCH = "${MACHINE_ARCH}"
TARGET_CC_ARCH += "${LDFLAGS}"
S = "${WORKDIR}"

do_compile() {
	${CC} DTool.c -w -Wall -g -O0 $(CFLAGS_EXTRA) -o qfotatool
}

do_install() {
	install -d ${D}/usr/local/bin
	install -m 0755 ${S}/qfotatool ${D}/usr/local/bin/qfotatool
}

FILES_${PN}-dbg = "/usr/src/debug /usr/local/bin/.debug"
FILES_${PN} = "/usr/local/bin/"
