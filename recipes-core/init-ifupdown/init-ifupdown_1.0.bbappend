# Look for file in this layer first
FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}-${PV}:"
PR:append = "+viv13"
SRC_URI += "file://interfaces"

do_install:append() {
        install -m 0644 ${WORKDIR}/interfaces ${D}${sysconfdir}/network/interfaces
}

