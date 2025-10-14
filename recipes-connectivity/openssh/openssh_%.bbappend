FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://banner"

do_install:append() {
        install -m 0644 ${WORKDIR}/banner ${D}${sysconfdir}/ssh/
}
