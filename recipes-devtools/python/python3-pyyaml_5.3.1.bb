DESCRIPTION = "Python YAML library"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=7bbd28caa69f81f5cd5f48647236663d"

PR = "r1"
PYPI_PACKAGE = "PyYAML"

SRC_URI[md5sum] = "d3590b85917362e837298e733321962b"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native nativesdk"


inherit setuptools3 pypi
