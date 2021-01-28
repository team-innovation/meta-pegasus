BBCLASSEXTEND = "native nativesdk"

do_install_append() {
	install -d ${D}${bindir}
	install -m 0755 ${S}/serial/tools/miniterm.py ${D}${bindir}
	sed -i 's#usr/bin/env python#usr/bin/env python3#' ${D}/${bindir}/miniterm.py
}


FILES_${PN} += "${bindir}/miniterm.py"
