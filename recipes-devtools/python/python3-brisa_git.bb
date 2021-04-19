SUMMARY = "Python Brisa"
DESCRIPTION = "Brisa UPnP library from maemo project"
HOMEPAGE = "https:///github.com/aleixq/python3-brisa"
LICENSE = "MIT"
SECTION = "devel/python"
LIC_FILES_CHKSUM = "file://LICENSE;md5=6b68f8ffe456195373f3cfb1bd43ef1a"

inherit setuptools3

SRCREV = "6815632cd377f456eedfc5b4d203a107e291fc8a"
SRC_URI="git://github.com/aleixq/python3-brisa.git;protocol=git"
S = "${WORKDIR}/git"

RDEPENDS_${PN} = "\
  ${PYTHON_PN} \
  ${PYTHON_PN}-requests \
  ${PYTHON_PN}-cherrypy \
"

do_install_append() {
    sed -i  '1i#!/usr/bin/python3\n' ${D}/${bindir}/brisa-conf
}

INSANE_SKIP_${PN} += "file-rdeps"
