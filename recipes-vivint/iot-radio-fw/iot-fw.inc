HOMEPAGE = "http://www.vivint.com/"
SECTION = "base"
LICENSE = "CLOSED"

SRC_URI = "file://${FW_NAME}"

FW_DIR = "/lib/firmware/vivint"

S = "${WORKDIR}"

do_compile[noexec] = "1"
do_runstrip[noexec] = "1"

do_install () {
    install -d ${D}${FW_DIR}
    install -m 644 ${S}/${FW_NAME} ${D}${FW_DIR}
}

FILES_${PN} = "\
    ${FW_DIR}/${FW_NAME} \
    "
