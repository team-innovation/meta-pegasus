DESCRIPTION = "Timeout context manager for asyncio programs"
HOMEPAGE = "https://pypi.org/project/async_timeout/"
SECTION = "devel/python"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=e3fc50a88d0a364313df4b21ef20c29e"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native nativesdk"

inherit setuptools3 pypi 

SRC_URI[md5sum] = "0a7872948573f9ef17f9ecb484024c21"
SRC_URI[sha256sum] = "b3c0ddc416736619bd4a95ca31de8da6920c3b9a140c64dbef2b2fa7bf521287"
