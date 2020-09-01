SUMMARY = "Python datetime with timezones"
DESCRIPTION = "Drop-in replacement for Python datetime which deeply cares about timezones"
HOMEPAGE = "https://github.com/mithro/python-datetime-tz"
SECTION = "devel/python"
LIC_FILES_CHKSUM = "file://PKG-INFO;md5=6c378feef98df86af27a6bd8fb30d5fe"
LICENSE = "Apache-2.0"

SRC_URI[md5sum] = "310e9aba1fdeb7e4ef80a394e725bfc6"
SRC_URI[sha256sum] = "bfa3bf297b632bf1bb216eb1f974d3155dfe4aee1245b186444306378603cf00"

PYPI_PACKAGE = "python-datetime-tz"
inherit setuptools3 pypi

RDEPENDS_${PN} = "\
    ${PYTHON_PN}-pytz \
    ${PYTHON_PN}-dateutil \
"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native nativesdk"

