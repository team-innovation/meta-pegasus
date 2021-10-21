HOMEPAGE = "https://github.com/boto/boto3"
DESCRIPTION = "Boto3 is the Amazon Web Services (AWS) Software Development Kit (SDK) for Python"
SECTION = "devel/python"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=2ee41112a44fe7014dce33e26468ba93"

SRC_URI[md5sum] = "bee3d41ffa88be078100b02ad1dc46db"
SRC_URI[sha256sum] = "819890e92268d730bdef1d8bac08fb069b148bec21f2172a1a99380798224e1b"

SRCNAME = "boto3"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native"

inherit pypi setuptools3
