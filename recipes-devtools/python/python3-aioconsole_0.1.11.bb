DESCRIPTION = "Asynchronous console and interfaces for asyncio"
HOMEPAGE = "https://pypi.org/project/aioconsole/"
SECTION = "devel/python"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://README.rst;md5=7272a2301b319f360c9b583cc9020fa7"

PR = "r2"

SRC_URI[md5sum] = "019845651ddd50d119c3222a5d20e142"
SRC_URI[sha256sum] = "8c009bb38b67beb018f301e1e032f78dc4ab86dab5c8a782462cb16adde94e76"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native"

inherit setuptools3 pypi
