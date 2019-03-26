FILESEXTRAPATHS_prepend := "${THISDIR}/files:${THISDIR}/busybox:"

PR = "r38"

SRC_URI += "file://vivintbusybox.cfg \
	    file://0001-allow-bootchartd-to-run-longer-than-one-minute.patch \
	    file://httpd.conf \
	    "

INITSCRIPT_PARAMS_${PN}-httpd = "defaults"

do_install_append () {
 install -m 0644 ${WORKDIR}/httpd.conf ${D}${sysconfdir}
}
