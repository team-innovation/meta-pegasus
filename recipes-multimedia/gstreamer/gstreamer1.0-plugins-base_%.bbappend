RPROVIDES_${PN}-opus += "${PN}-opus"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
SRC_URI_append = " file://0001-use-rtsps-scheme-for-tls-transport-methods.patch \
		   "
do_install_append() {
    if [ "${MLPREFIX}" == "lib32-" ]; then
        install -d ${D}${bindir}/32
        install -d ${D}${libexecdir}/32
        mv ${D}${bindir}/gst-* ${D}${bindir}/32
        mv ${D}${libexecdir}/gstreamer-1.0 ${D}${libexecdir}/32
    fi
}

FILES_${PN} += "${bindir}/32"
FILES_${PN} += "${libexecdir}/32"
