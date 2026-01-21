FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

PR = "r1"

SRC_URI += "file://wpa-supplicant.sh"

do_install:append() {
        install -d ${D}/etc/network/if-pre-up.d
        install -m 0755 ${WORKDIR}/wpa-supplicant.sh ${D}/etc/network/if-pre-up.d/wpa-supplicant
}

