BBCLASSEXTEND = "native nativesdk"

do_install_append() {
	install -d ${D}${bindir}
	install -m 0755 ${S}/serial/tools/miniterm.py ${D}${bindir}
}


FILES_${PN} += "${bindir}/miniterm.py"
