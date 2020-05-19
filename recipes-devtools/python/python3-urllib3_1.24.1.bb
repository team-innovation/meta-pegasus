DESCRIPTION = "Python urllib3"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=ea114851ad9a8c311aac8728a681a067"

inherit pypi setuptools3

SRC_URI[md5sum] = "f3d8b1841539200c949a33e87e551d8e"
SRC_URI[sha256sum] = "de9529817c93f27c8ccbfead6985011db27bd0ddfcdb2d86f3f663385c6a9c22"

RDEPENDS_${PN} += "\
    python3-certifi \
    python3-cryptography \
    python3-email \
    python3-idna \
    python3-netclient \
    python3-pyopenssl \
    python3-threading \
"

BBCLASSEXTEND = "native nativesdk"
