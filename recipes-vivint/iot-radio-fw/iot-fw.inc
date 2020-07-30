HOMEPAGE = "http://www.vivint.com/"
SECTION = "base"
LICENSE = "CLOSED"

SRC_URI = "file://${FW_HEX_NAME} \
	   file://ihex2fw.c \
          "

FW_DIR = "/lib/firmware/vivint"

S = "${WORKDIR}"


do_compile() {
        ${BUILD_CC} ihex2fw.c -o ihex2fw
        ./ihex2fw ${FW_HEX_NAME} ${FW_NAME}
}

do_install () {
    install -d ${D}${FW_DIR}
    install -m 644 ${S}/${FW_NAME} ${D}${FW_DIR}
}

FILES_${PN} = "\
    ${FW_DIR}/${FW_NAME} \
    "
