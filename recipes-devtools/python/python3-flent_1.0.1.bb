DESCRIPTION = "Flent FLExible network tool"
SECTION = "devel/python"
PRIORITY = "optional"
LICENSE = "CLOSED"
SRCNAME = "flent"
PR = "r0"

DEPENDS += "python3 netperf fping"
DEPENDS_virtclass-native += "python3-native"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native"

NATIVE_INSTALL_WORKS = "1"

SRC_URI = "https://github.com/tohojo/flent/archive/v1.0.1.tar.gz"
SRC_URI += "file://flent_use_distutils_for_yocto.patch"

SRC_URI[md5sum] = "7b3175eee98e60107f03da2c62a83a79"
SRC_URI[sha256sum] = "1bd2ecfc3e731ff3df161716604aaccb5d945c5040c2c6e512692e373224498c"

S = "${WORKDIR}/${SRCNAME}-${PV}"

inherit distutils3

do_install_prepend() {
    install -d ${D}/${libdir}/${PYTHON_DIR}/site-packages
}

RDEPENDS_${PN} = "\
  python3-distutils \
"

