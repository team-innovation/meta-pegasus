DESCRIPTION = "This is a Python client library for iterating over http Server Sent Event (SSE) streams"
HOMEPAGE = "https://pypi.org/project/sseclient/"
SECTION = "devel/python"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=e04f70f331d3fc1e7623959f9a48244c"

DEPENDS += "python3-aiohttp-sse"

PR = "r1"

SRC_URI[md5sum] = "f512abac1dda6d096f7f62714e4b83ae"
SRC_URI[sha256sum] = "b2fe534dcb33b1d3faad13d60c5a7c718e28f85987f2a034ecf5ec279918c11c"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native nativesdk"


inherit setuptools3 pypi
