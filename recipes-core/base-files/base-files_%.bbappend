FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

do_install_append() {
    install -d ${D}/media/extra
    install -d ${D}/media/mmcblk2p5
    install -d ${D}/media/mmcblk2p6
}

FILES_${PN} += " /media"
