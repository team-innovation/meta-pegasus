FILESEXTRAPATHS_prepend := "${THISDIR}/files:${THISDIR}/busybox:"

PR = "r35"

SRC_URI += "file://vivintbusybox.cfg \
	    file://0001-allow-bootchartd-to-run-longer-than-one-minute.patch \
	    "

INITSCRIPT_PARAMS_${PN}-httpd = "defaults"
