FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

PR = "3"

SRC_URI += "file://abgr-support.patch \
	    file://weston.ini"
