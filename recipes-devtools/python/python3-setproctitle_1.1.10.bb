SUMMARY = "Set Proc Title Support for Python"
HOMEPAGE = "https://github.com/dvarrazzo/py-setproctitle"
SECTION = "devel/python"
LICENSE = "PSF"
LIC_FILES_CHKSUM = "file://COPYRIGHT;md5=a1f1f516ff59e8899115754de850190f"

SRC_URI[md5sum] = "2dcdd1b761700a5a13252fea3dfd1977"
SRC_URI[sha256sum] = "6283b7a58477dd8478fbb9e76defb37968ee4ba47b05ec1c053cb39638bd7398"

PYPI_PACKAGE = "setproctitle"
inherit setuptools3 pypi

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native nativesdk"
