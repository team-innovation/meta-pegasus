SUMMARY = "BRisa is a UPnP framework."
HOMEPAGE = "https://github.com/aleixq/python3-brisa"
SECTION = "devel/python"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5="

PYPI_PACKAGE = "python3-brisa"

SRC_URI[sha256sum] = "b90d1ddffa1b2f0a6e000958c498d80853b82bc8fd55b9d2398f69df4a8f455a"
S = "${WORKDIR}/brisa-${PV}"

inherit setuptools3 pypi