SUMMARY = "High-level asynchronous FTP server library"
HOMEPAGE = "https://github.com/giampaolo/pyftpdlib/"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=f81ad04756fa5632f5ab66448ec5cbaf"

SRC_URI[md5sum] = "459dd3e685c4b7dfcf574ca05e5586eb"
SRC_URI[sha256sum] = "fda655d81f29af52885ca2f8a2704134baed540f16d66a0b26e8fdfafd12db5e"

PYPI_PACKAGE = "pyftpdlib"
inherit setuptools3 pypi

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native nativesdk"
