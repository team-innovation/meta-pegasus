DESCRIPTION = "Live555 Proxy Server Module for Python"
SECTION = "devel/python"
PRIORITY = "optional"
LICENSE = "CLOSED"
SRCNAME = "live555-proxy-server"
PR = "ml24"

DEPENDS += "python3 live555"
#RDEPENDS = "live555-lib"
RDEPENDS_${PN} = "live555-libUsageEnvironment live555-libBasicUsageEnvironment live555-libgroupsock live555-libliveMedia"
DEPENDS_virtclass-native += "python3-native"

SRCREV = "0f078a5ae218"
MODULE = "live555-proxy-server"
SRC_URI = "hg://${HG_SERVER};module=${MODULE};protocol=http"
#SRC_URI = "file:///home/craig/hg/live555-proxy-server"

S = "${WORKDIR}/${SRCNAME}"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native"

NATIVE_INSTALL_WORKS = "1"

inherit distutils3

do_configure_prepend() {
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
