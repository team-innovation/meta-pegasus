SUMMARY = "Simple Python module defined by PEP 484."
HOMEPAGE = "https://github.com/python/typing"
LICENSE = "PSF"
LIC_FILES_CHKSUM = "file://LICENSE;md5=64fc2b30b67d0a8423c250e0386ed72f"
SRCNAME = "typing"

PR = "r3"

SRC_URI = "https://files.pythonhosted.org/packages/bf/9b/2bf84e841575b633d8d91ad923e198a415e3901f228715524689495b4317/${SRCNAME}-${PV}.tar.gz"

SRC_URI[md5sum] = "64614206b4bdc0864fc0e0bccd69efc9"
SRC_URI[sha256sum] = "4027c5f6127a6267a435201981ba156de91ad0d1d98e9ddc2aa173453453492d"

S = "${WORKDIR}/${SRCNAME}-${PV}"

inherit pythonnative

NATIVE_INSTALL_WORKS = "1"

inherit setuptools python-dir

RDEPENDS_${PN} = "\
  python-core \
  python-io \
  python-terminal \
  python-resource \
  python-fcntl \
"
