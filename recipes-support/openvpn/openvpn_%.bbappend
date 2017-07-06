FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI += "file://procman.d    \
	   file://server.conf \
           file://openvpn \
           file://ca.crt  \
           file://ca.key  \
           file://dh1024.pem  \
           file://server.crt  \
           file://server.key    \ 
	"

do_install_append() {
    install -d ${D}/${sysconfdir}/init.d
    install -d ${D}/${sysconfdir}/procman.d
    install -m 755 ${WORKDIR}/openvpn ${D}/${sysconfdir}/init.d

    install -m 755 ${WORKDIR}/server.conf ${D}/${sysconfdir}/openvpn
    install -m 755 ${WORKDIR}/ca.crt ${D}/${sysconfdir}/openvpn
    install -m 755 ${WORKDIR}/ca.key ${D}/${sysconfdir}/openvpn
    install -m 755 ${WORKDIR}/dh1024.pem ${D}/${sysconfdir}/openvpn
    install -m 755 ${WORKDIR}/server.crt  ${D}/${sysconfdir}/openvpn
    install -m 755 ${WORKDIR}/server.key ${D}/${sysconfdir}/openvpn

    install -m 755 ${WORKDIR}/procman.d/* ${D}/${sysconfdir}/procman.d

    install -d ${D}/${sysconfdir}/openvpn
    install -d ${D}/${sysconfdir}/openvpn/sample
    install -m 755 ${S}/sample/sample-config-files/loopback-server  ${D}${sysconfdir}/openvpn/sample/loopback-server.conf
    install -m 755 ${S}/sample/sample-config-files/loopback-client  ${D}${sysconfdir}/openvpn/sample/loopback-client.conf
    install -dm 755 ${D}${sysconfdir}/openvpn/sample/sample-keys
    install -m 644 ${S}/sample/sample-keys/* ${D}${sysconfdir}/openvpn/sample/sample-keys
}

FILES_${PN} += "${sysconfdir}/procman.d"
