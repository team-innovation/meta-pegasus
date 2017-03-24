DESCRIPTION = "Live555 Proxy Server Module for Python"
SECTION = "devel/python"
PRIORITY = "optional"
LICENSE = "CLOSED"
SRCNAME = "live555-proxy-server"
PR = "ml48"

DEPENDS += "python3 live555"
#RDEPENDS = "live555-lib"
RDEPENDS_${PN} = "live555-libusageenvironment live555-libbasicusageenvironment live555-libgroupsock live555-liblivemedia"
DEPENDS_virtclass-native += "python3-native"

SRCREV = "19cdffbd5f42"
MODULE = "live555-proxy-server"
SRC_URI = "hg://${HG_SERVER};module=${MODULE};protocol=http"
#SRC_URI = "file:///home/craig/hg/live555-proxy-server"

S = "${WORKDIR}/${SRCNAME}"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native"

NATIVE_INSTALL_WORKS = "1"

inherit distutils3

do_configure_prepend() {
#	cp -a ${WORKDIR}/home/craig/live555-proxy-server/* ${S}
}

do_install_prepend() {
    install -d ${D}/${libdir}/${PYTHON_DIR}/site-packages
}

do_package_qa() {
    echo "Skipping QA ..."
}

RDEPENDS_${PN} = "\
  python3-distutils \
"

INSANE_SKIP_${PN} = "True"
