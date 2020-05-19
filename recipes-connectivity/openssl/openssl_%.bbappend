FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI_append += "file://openssl_ld.patch \
		   file://openssl.patch"



