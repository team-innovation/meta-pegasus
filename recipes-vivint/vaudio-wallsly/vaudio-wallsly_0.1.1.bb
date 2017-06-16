CRIPTION = "User vaudio-wallsly for Wallsly Microsemi"
SECTION = "utils"
LICENSE = "BSD-2-Clause"
LIC_FILES_CHKSUM = "file://COPYING;md5=d73bd2cc27d46d711cf1ba1d489eaa43"
PR = "r0"
SRCREV = "${AUTOREV}"
SRC_URI = "git://git.vivint.com/vaudio-wallsly;protocol=git;branch=master"
RDEPENDS_${PN} = "libpulse-simple libpulse libasound"
S = "${WORKDIR}/git"
do_compile() {
   oe_runmake
}
#do_install() {
#    install -d ${D}${bindir}
#    cp -a ${S}/vaudio-wallsly ${D}${bindir}
#}
#FILES_${PN} = "${bindir}/vaudio-wallsly"

do_install_append() {
        install -d ${D}/usr/local/bin
        install -m 0755 ${WORKDIR}/${MODULE}/git/vaudio-wallsly ${D}/usr/local/bin
}

FILES_${PN}-dbg += "/usr/local/bin/.debug"
FILES_${PN} = "/usr/local/bin/*"
