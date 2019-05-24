SUMMARY = "Simple Python module defined by PEP 484."
HOMEPAGE = "https://github.com/python/typing"
LICENSE = "PSF"
LIC_FILES_CHKSUM = "file://LICENSE;md5=64fc2b30b67d0a8423c250e0386ed72f"
SRCNAME = "typing"

SRC_URI = "https://pypi.python.org/packages/b6/0c/53c42edca789378b8c05a5496e689f44e5dd82bc6861d1ae5a926ee51b84/${SRCNAME}-${PV}.tar.gz"

SRC_URI[md5sum] = "3996a747158e5591abf689c1c5f8f9db"
SRC_URI[sha256sum] = "ca2daac7e393e8ee86e9140cd0cf0172ff6bb50ebdf0b06281770f98f31bff21"

S = "${WORKDIR}/${SRCNAME}-${PV}"

inherit pythonnative

NATIVE_INSTALL_WORKS = "1"

inherit setuptools

RDEPENDS_${PN} = "\
  python3-core \
  python3-io \
  python3-terminal \
  python3-resource \
  python3-fcntl \
  python3-ptyprocess \
"
RDEPENDS_${PN}_class-native = ""
