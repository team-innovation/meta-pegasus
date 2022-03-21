SUMMARY = "Python TUN/TAP interface"
DESCRIPTION = "Python module which let you create TUN/TAP device very easily."
HOMEPAGE = "https://github.com/montag451/pytun"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=13f7629e8e4989b66b4a913ab05a91de"
PR = "r1"
SRC_NAME = "python-pytun"

SRC_URI[md5sum] = "4c426775e9a5699977c1fbb0f5ee8452"
SRC_URI[sha256sum] = "20b53ea7a09dfe173c00ec0a00eea508b05e959f5dc4b4bb698aa52252192f8f"
SRC_URI = "https://files.pythonhosted.org/packages/b3/74/4fecb3eeb2de2e672db6eca5088afa63541f0abeaaef5b7c6549f430c084/${SRC_NAME}-${PV}.tar.gz"

PYPI_PACKAGE = "python-pytun"
inherit setuptools3 pypi

BBCLASSEXTEND = "native nativesdk"