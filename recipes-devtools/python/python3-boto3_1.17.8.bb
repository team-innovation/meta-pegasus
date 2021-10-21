HOMEPAGE = "https://github.com/boto/boto3"
DESCRIPTION = "Boto3 is the Amazon Web Services (AWS) Software Development Kit (SDK) for Python"
SECTION = "devel/python"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=2ee41112a44fe7014dce33e26468ba93"

SRCNAME = "boto3"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native"

inherit pypi setuptools3
