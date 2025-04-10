SUMMARY = "A drop in replacement for Python's datetime module which cares deeply about timezones."
HOMEPAGE = "https://github.com/mithro/python-datetime-tz"
SECTION = "devel/python"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5="

PYPI_PACKAGE = "python-datetime-tz"

SRC_URI[sha256sum] = "bfa3bf297b632bf1bb216eb1f974d3155dfe4aee1245b186444306378603cf00"

RDEPENDS.${PN} = "\
    ${PYTHON_PN}-pytz \
    ${PYTHON_PN}-dateutil \
"

inherit setuptools3 pypi