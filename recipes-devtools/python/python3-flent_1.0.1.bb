DESCRIPTION = "Flent FLExible network tool"
SECTION = "devel/python"
PRIORITY = "optional"
LICENSE = "CLOSED"
SRCNAME = "flent"
PR = "r3"

RDEPENDS_${PN} = "python3-xmlrpc"
RDEPENDS_${PN} += "iperf (>= 1.9)"                                                                                                                           
RDEPENDS_${PN} += "netperf (>= 2.6)"                                                                                                                         
RDEPENDS_${PN} += "fping (>= 3.5)"  

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native nativesdk"

INSANE_SKIP_${PN} = "installed-vs-shipped"

SRC_URI = "https://github.com/tohojo/flent/archive/v1.0.1.tar.gz"

SRC_URI[md5sum] = "7b3175eee98e60107f03da2c62a83a79"
SRC_URI[sha256sum] = "1bd2ecfc3e731ff3df161716604aaccb5d945c5040c2c6e512692e373224498c"

S = "${WORKDIR}/${SRCNAME}-${PV}"

inherit setuptools3 

do_install_prepend() {
    install -d ${D}/${libdir}/${PYTHON_DIR}/site-packages
    install -d ${D}/${libdir}/${PYTHON_DIR}/site-packages/flent/tests
    install -d ${D}/${libdir}/${PYTHON_DIR}/site-packages/flent/scripts
    echo "Copy " ${S}/flent/tests "to" ${D}/${libdir}/${PYTHON_DIR}/site-packages/flent/tests/
    cp -r ${S}/flent/tests ${D}/${libdir}/${PYTHON_DIR}/site-packages/flent/
    cp -r ${S}/flent/scripts ${D}/${libdir}/${PYTHON_DIR}/site-packages/flent/
}

do_install_append() {
    install -d ${D}/${bindir}
    install -m 0755 ${S}/flent/__main__.py ${D}/${bindir}/flent
    sed -i  '1i#!/usr/bin/python3\n' ${D}/${bindir}/flent
}

CLEANBROKEN = "1"
