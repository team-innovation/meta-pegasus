SUMMARY = "A drop in replacement for Python's datetime module which cares deeply about timezones."
HOMEPAGE = "https://github.com/mithro/python-datetime-tz"
SECTION = "devel/python"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5="

PYPI_PACKAGE = "python-datetime-tz"

SRC_URI[sha256sum] = ""

RDEPENDS.${PN} = "\
    ${PYTHON_PN}-pytz \
    ${PYTHON_PN}-dateutil \
"

inherit setuptools3 pypi