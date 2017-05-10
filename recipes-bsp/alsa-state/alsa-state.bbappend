#   another file extension for new patch to the append in the meta-fsl-arm

# Append path for freescale layer to include alsa-state asound.conf
FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI_append_mx6 = " \
        file://asound.state.sly \
"

do_install_append_mx6() {
    install -m 0644 ${WORKDIR}/asound.state.sly ${D}${localstatedir}/lib/alsa/asound.state
}

