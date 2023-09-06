DESCRIPTION = "GPU G2D library stub"
LICENSE = "Proprietary"

PROVIDES += "virtual/libg2d"

do_install () {
    install -d ${D}${libdir}
    install -d ${D}${includedir}    
}

FILES_${PN} = "${libdir}"
FILES_${PN}-dev = "${includedir}"
