HOMEPAGE = "https://github.com/jmespath/jmespath.py"
DESCRIPTION = "JMESPath (pronounced “james path”) allows you to declaratively specify how to extract elements from a JSON document."
SECTION = "devel/python"
LICENSE = "Amazon"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=2683790f5fabb41a3f75b70558799eb4"

SRC_URI[md5sum] = "65bdcb5fa5bcf1cc710ffa508e78e408"
SRC_URI[sha256sum] = "b85d0567b8666149a93172712e68920734333c0ce7e89b78b3e987f71e5ed4f9"

SRCNAME = "jmespath"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native"

inherit pypi setuptools3
