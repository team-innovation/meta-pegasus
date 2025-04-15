SUMMARY = "Server-sent events support for aiohttp."
HOMEPAGE = "https://github.com/aio-libs/aiohttp-sse"
SECTION = "devel/python"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=ef8862d84cc786a464fcdb49f19312c9"

SRC_URI[sha256sum] = "a48dd5774031d3f41a29e159ebdbb93e89c8f37c1e9e83e196296be51885a5c2"

RDEPENDS:${PN} = "${PYTHON_PN}-aiohttp"

inherit setuptools3 pypi