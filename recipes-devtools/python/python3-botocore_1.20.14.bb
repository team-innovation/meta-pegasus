HOMEPAGE = "https://github.com/boto/botocore"
DESCRIPTION = "Botocore is the Amazon Web Services (AWS) low-level interface to a number of services, but for our purposes: the foundation for the AWS CLI as well as boto3"
SECTION = "devel/python"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=2ee41112a44fe7014dce33e26468ba93"

SRCNAME = "botocore"

SRC_URI = "https://files.pythonhosted.org/packages/90/54/596c1dcd0d36d93be8bd843cc3fc895ab796eeb7a8676b679248c8a998c6/botocore-1.20.14.tar.gz"

S = "${WORKDIR}/${SRCNAME}-${PV}"

SRC_URI[md5sum] = "22482c26b54b56bab282d76400c49d3f"
SRC_URI[sha256sum] = "2a07533de92603607c8b594ff92647f5d5a39e75f66c9476ccd30ed4d6de37ae"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native"

NATIVE_INSTALL_WORKS = "1"

inherit pypi setuptools3 python3-dir
