HOMEPAGE = "https://github.com/boto/boto3"
DESCRIPTION = "Boto3 is the Amazon Web Services (AWS) Software Development Kit (SDK) for Python"
SECTION = "devel/python"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=2ee41112a44fe7014dce33e26468ba93"

SRCNAME = "boto3"

SRC_URI = "https://files.pythonhosted.org/packages/79/94/8cdeff002e0c9ab56f88560523036d8efcb7680e02abe2fa4fc8f4bd87c2/boto3-1.17.8.tar.gz"

S = "${WORKDIR}/${SRCNAME}-${PV}"

SRC_URI[md5sum] = "bee3d41ffa88be078100b02ad1dc46db"
SRC_URI[sha256sum] = "819890e92268d730bdef1d8bac08fb069b148bec21f2172a1a99380798224e1b"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native"

NATIVE_INSTALL_WORKS = "1"

inherit pypi setuptools3 python3-dir
