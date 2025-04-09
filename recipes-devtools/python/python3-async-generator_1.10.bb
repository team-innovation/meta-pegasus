SUMMARY = "Async generators and context managers for Python 3.5+"
HOMEPAGE = "https://github.com/python-trio/async_generator"
SECTION = "devel/python"

LICENSE = "MIT | Apache-2.0"
LIC_FILES_CHKSUM = "\
    file://LICENSE.MIT;md5=e62ba5042d5983462ad229f5aec1576c \
    file://LICENSE.APACHE2;md5=3b83ef96387f14655fc854ddc3c6bd57 \
"

PYPI_PACKAGE = "async_generator"
SRC_URI[sha256sum] = "6ebb3d106c12920aaae42ccb6f787ef5eefdcdd166ea3d628fa8476abe712144"

inherit setuptools3 pypi