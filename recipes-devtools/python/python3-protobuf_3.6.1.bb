DESCRIPTION = "Protocol Buffers"
HOMEPAGE = "https://developers.google.com/protocol-buffers/"
SECTION = "devel/python"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://MANIFEST.in;md5=2031594b571227c33958e158d982764c"

PR = "r1"
RDEPENDS_${PN} += " \
    ${PYTHON_PN}-datetime \
    ${PYTHON_PN}-json \
    ${PYTHON_PN}-logging \
    ${PYTHON_PN}-netclient \
    ${PYTHON_PN}-numbers \
    ${PYTHON_PN}-pkgutil \
    ${PYTHON_PN}-six \
    ${PYTHON_PN}-unittest \
"

SRC_URI[md5sum] = "e2daef80e70249aa1f05363670c6b3f8"
SRC_URI[sha256sum] = "1489b376b0f364bcc6f89519718c057eb191d7ad6f1b395ffd93d1aa45587811"


# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native nativesdk"

inherit setuptools3 pypi
