SUMMARY = "Quectel FOTA Tool"
DESCRIPTION = "Tool for performing local FOTA firmware updates"
SECTION = "base"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://DTool.c;md5=fbcd3a67045963559e7094b6356afaa0"
PR = "r1"

SRC_URI = "file://DTool.c"

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
