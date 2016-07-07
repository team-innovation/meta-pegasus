FILESEXTRAPATHS_prepend := "${THISDIR}/files:${THISDIR}/busybox:"

SRC_URI += "file://vivintbusybox.cfg"

INITSCRIPT_PARAMS_${PN}-httpd = "defaults"
