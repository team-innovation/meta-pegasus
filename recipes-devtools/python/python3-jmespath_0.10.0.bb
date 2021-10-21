HOMEPAGE = "https://github.com/jmespath/jmespath.py"
DESCRIPTION = "JMESPath (pronounced “james path”) allows you to declaratively specify how to extract elements from a JSON document."
SECTION = "devel/python"
LICENSE = "Amazon"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=2683790f5fabb41a3f75b70558799eb4"

SRCNAME = "jmespath"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native"

inherit pypi setuptools3
