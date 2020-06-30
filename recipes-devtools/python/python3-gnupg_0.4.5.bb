DESCRIPTION = "Python interface to gnupg"
SECTION = "devel/python"
PRIORITY = "optional"
LICENSE = "BSD-2-Clause"
SRCNAME = "python-gnupg"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=b0b7ac63b60085b23fa9f7e1951daa1d"
PYPI_PACKAGE = "python-gnupg"

RDEPENDS_${PN} = "python3-core gnupg"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native nativesdk"

inherit setuptools3 pypi python3-dir

SRC_URI[md5sum] = "366b574e447f9e99951fcb6d0290ecc1"
SRC_URI[sha256sum] = "3353e59949cd2c15efbf1fca45e347d8a22f4bed0d93e9b89b2657bda19cec05"

