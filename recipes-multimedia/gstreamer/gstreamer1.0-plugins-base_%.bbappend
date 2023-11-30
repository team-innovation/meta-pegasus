RPROVIDES_${PN}-opus += "${PN}-opus"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
SRC_URI_append = " file://0001-use-rtsps-scheme-for-tls-transport-methods.patch \
		   "
do_install_append() {
    if [ "${MLPREFIX}" == "lib32-" ]; then
        install -d ${D}${bindir}/32
        install -d ${D}${libexecdir}/32
        mv ${D}${bindir}/gst-* ${D}${bindir}/32
    fi
}
