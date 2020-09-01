SUMMARY = "Python cov"
HOMEPAGE = "https://github.com/pytest-dev/pytest-cov"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=cbc4e25353c748c817db2daffe605e43"

SRC_URI[md5sum] = "13e17f0b4f263a04fb1fadde7ebe2fbb"
SRC_URI[sha256sum] = "47bd0ce14056fdd79f93e1713f88fad7bdcc583dcd7783da86ef2f085a0bb88e"

PYPI_PACKAGE = "pytest-cov"
inherit pypi setuptools3 

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native nativesdk"
