SUMMERY = "Async DNS resolver"
DESCRIPTION = "Simple DNS resolver for asyncio"
HOMEPAGE = "https://github.com/saghul/aiodns"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=a565d8b5d06b9620968a135a2657b093"

SRC_URI[md5sum] = "3e121f9eb7ef3ba3556ba7ec28c6f63a"
SRC_URI[sha256sum] = "815fdef4607474295d68da46978a54481dd1e7be153c7d60f9e72773cd38d77d"

PYPI_PACKAGE = "aiodns"
inherit setuptools3 pypi

RDEPENDS_${PN} = "\
    ${PYTHON_PN}-asyncio \
"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native nativesdk"
