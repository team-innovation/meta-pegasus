HOMEPAGE = "https://github.com/jmespath/jmespath.py"
DESCRIPTION = "JMESPath (pronounced “james path”) allows you to declaratively specify how to extract elements from a JSON document."
SECTION = "devel/python"
LICENSE = "Amazon"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=2683790f5fabb41a3f75b70558799eb4"

SRCNAME = "jmespath"

SRC_URI = "https://files.pythonhosted.org/packages/3c/56/3f325b1eef9791759784aa5046a8f6a1aff8f7c898a2e34506771d3b99d8/jmespath-0.10.0.tar.gz"

S = "${WORKDIR}/${SRCNAME}-${PV}"

SRC_URI[md5sum] = "65bdcb5fa5bcf1cc710ffa508e78e408"
SRC_URI[sha256sum] = "b85d0567b8666149a93172712e68920734333c0ce7e89b78b3e987f71e5ed4f9"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native"

NATIVE_INSTALL_WORKS = "1"

inherit pypi setuptools3 python3-dir
