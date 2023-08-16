RPROVIDES_${PN}-opus += "${PN}-opus"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
SRC_URI_append = " file://0001-use-rtsps-scheme-for-tls-transport-methods.patch \
		   "
