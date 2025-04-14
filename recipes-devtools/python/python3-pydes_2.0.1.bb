SUMMARY = "Pure python implementation of DES and TRIPLE DES encryption algorithm"
HOMEPAGE = "https://github.com/twhiteman/pyDes"
SECTION = "devel/python"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5="

PYPI_PACKAGE = "pydes"

SRC_URI = "https://files.pythonhosted.org/packages/92/5e/0075a35ea5d307a182b0963900298b209ea2f363ccdd5a27e8cb04c58410/pyDes-${PV}.tar.gz"
SRC_URI[sha256sum] = ""

inherit setuptools3 pypi