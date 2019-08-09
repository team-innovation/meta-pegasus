DESCRIPTION = "Python interface to gnupg"
SECTION = "devel/python"
PRIORITY = "optional"
LICENSE = "BSD-2-Clause"
SRCNAME = "python-gnupg"
LIC_FILES_CHKSUM = "file://LICENSE;md5=5b3cbf3c3220969afbf461f4a6ac97c9"
PR = "ml1"
PYPI_PACKAGE = "python-gnupg"

RDEPENDS_${PN} = "python3-core gnupg"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native"

inherit setuptools3 pypi

SRC_URI[md5sum] = "27415bead227e8c6906900b7c777120c"
SRC_URI[sha256sum] = "ffdfad1824fbde8ab94c50e08040edd6a82b4095c187994954471a38c45a094a"

