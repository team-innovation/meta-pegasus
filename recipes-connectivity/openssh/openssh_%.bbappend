FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

PR = "r11"

SRC_URI += "file://environment"

do_install_append() {
    install -d ${D}/home/root/.ssh
    install -m 0644 ${WORKDIR}/environment ${D}/home/root/.ssh
}


FILES_${PN} += "/home/root/.ssh/environment"
