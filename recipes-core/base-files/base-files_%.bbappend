FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

do_install_append() {
    install -d ${D}/media/extra
    install -d ${D}/media/mmcblk2p2
    install -d ${D}/media/mmcblk2p3
}

FILES_${PN} += " /media"
