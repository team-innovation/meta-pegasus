FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

do_install:append() {
    install -d ${D}/media/extra
}

FILES_${PN} += " /media"
