DESCRIPTION = "Live555 Proxy Server Module for Python"
SECTION = "devel/python"
PRIORITY = "optional"
LICENSE = "CLOSED"
SRCNAME = "live555-proxy-server"
PR = "ml54"

DEPENDS += "python3 live555"
#RDEPENDS = "live555-lib"
RDEPENDS_${PN} = "live555-libusageenvironment live555-libbasicusageenvironment live555-libgroupsock live555-liblivemedia"

SRCREV = "04c853797497f3b8c7bd4d5d76024f50db286b92"
MODULE = "live555-proxy-server"
SRC_URI = "git://${GIT_SERVER}/${MODULE};protocol=ssh"

S = "${WORKDIR}/git"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native"

NATIVE_INSTALL_WORKS = "1"

inherit setuptools3 python3-dir

do_install_prepend() {
    install -d ${D}/${libdir}/${PYTHON_DIR}/site-packages
}

do_package_qa() {
    echo "Skipping QA ..."
}

INSANE_SKIP_${PN} = "True"
