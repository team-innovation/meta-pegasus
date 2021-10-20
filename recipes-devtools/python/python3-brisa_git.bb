SUMMARY = "Python Brisa"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=6b68f8ffe456195373f3cfb1bd43ef1a"
PR = "r1"

inherit setuptools3

SRCREV = "6815632cd377f456eedfc5b4d203a107e291fc8a"
SRC_URI="git://github.com/aleixq/python3-brisa.git;protocol=git"
S = "${WORKDIR}/git"

RDEPENDS_${PN} = "\
  python3-core \
  python3-requests \
  python3-cherrypy \
"
do_install_append() {
    sed -i  '1i#!/usr/bin/python3\n' ${D}/${bindir}/brisa-conf
}
