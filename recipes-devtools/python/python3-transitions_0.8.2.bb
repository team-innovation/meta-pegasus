SUMMARY = "Transitions - A lightweight, object-oriented finite state machine implementation"
HOMEPAGE = "https://github.com/pytransitions/transitions"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=baac7be1f4c17620df74188e23da6d47"

SRC_URI[md5sum] = "86c751a6a40ac8931b1049348b9a82a6"
SRC_URI[sha256sum] = "6ff7a3bfa4ac64b62993bb19dc2bb6a0ccbdf4e70b2cbae8350de6c916d77748"

PYPI_PACKAGE = "transitions"
inherit setuptools3 pypi

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native nativesdk"
