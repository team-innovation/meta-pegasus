SUMMARY = "BRisa is a UPnP framework."
HOMEPAGE = "https://github.com/aleixq/python3-brisa"
SECTION = "devel/python"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5="

PYPI_PACKAGE = "python3-brisa"
SRC_URI[sha256sum] = ""
S = "${WORKDIR}/brisa-${PV}"

inherit setuptools3