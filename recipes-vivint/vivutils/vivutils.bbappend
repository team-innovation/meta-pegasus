FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI_append = "\
                  file://encryptdrive \
                  file://decryptdrive \
"

do_install_append() {
    install -d ${D}/usr/local/bin
    install -m 0755 ${S}/encryptdrive ${D}/usr/local/bin
    install -m 0755 ${S}/decryptdrive ${D}/usr/local/bin
}
