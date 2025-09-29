FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI += "\
    file://wifi-station \
"

do_install:append() {
    install -D -m0644 ${WORKDIR}/wifi-station ${D}${systemd_unitdir}/network/80-wifi-station.network
}
