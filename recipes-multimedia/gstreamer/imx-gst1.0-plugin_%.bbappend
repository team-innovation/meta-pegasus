FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

PR="1"

SRC_URI_append = " file://addmulaw.patch \
		   "
