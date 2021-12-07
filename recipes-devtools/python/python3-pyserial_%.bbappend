BBCLASSEXTEND = "native nativesdk"

do_install_append() {
	install -d ${D}${bindir}
	sed -i 's#usr/bin/env python$#usr/bin/env python3#' ${S}/serial/tools/miniterm.py
	install -m 0755 ${S}/serial/tools/miniterm.py ${D}${bindir}
}


FILES_${PN} += "${bindir}/miniterm.py"
