SUMMARY = "Asynchronous console and interfaces for asyncio"
HOMEPAGE = "https://github.com/saghul/aiodns"
SECTION = "devel/python"

--TODO--

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=a565d8b5d06b9620968a135a2657b093"

SRC_URI[md5sum] = "3e121f9eb7ef3ba3556ba7ec28c6f63a"
SRC_URI[sha256sum] = "815fdef4607474295d68da46978a54481dd1e7be153c7d60f9e72773cd38d77d"

RDEPENDS.${PN} = "${PYTHON_PN}-asyncio"

inherit setuptools3 pypi