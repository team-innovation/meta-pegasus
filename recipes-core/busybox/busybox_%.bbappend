FILESEXTRAPATHS_prepend := "${THISDIR}/files:${THISDIR}/busybox:"

PR = "r33"

SRC_URI += "file://vivintbusybox.cfg"

INITSCRIPT_PARAMS_${PN}-httpd = "defaults"
