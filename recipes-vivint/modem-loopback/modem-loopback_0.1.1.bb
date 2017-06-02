DESCRIPTION = "User modem loopback test for Wallsly"
SECTION = "utils"
LIC_FILES_CHKSUM = "file://COPYING;md5=71c0ac4d86266533509aa0825b8d323c"
LICENSE = "GPL"
PR = "r0"

SRC_URI = "git://git.vivint.com/modem-loopback;branch=master"

S = "${WORKDIR}/modem-loopback-${PV}"

do_compile() {
   oe_runmake
}

do_install() {
    install -d ${D}${bindir}
    cp -a ${S}/modem-loopback ${D}${bindir}
}

FILES_${PN} = "${bindir}/modem-loopback \

SRC_URI[md5sum] = "862f90bafda118c4d3c5ee6477e50841"
SRC_URI[sha256sum] = "0b9d53433387aa4f04634a6c63a5efa8203070f2298af72a705f9be3dda65af2"

