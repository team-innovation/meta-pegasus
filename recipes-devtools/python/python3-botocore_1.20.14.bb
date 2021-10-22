HOMEPAGE = "https://github.com/boto/botocore"
DESCRIPTION = "Botocore is the Amazon Web Services (AWS) low-level interface to a number of services, but for our purposes: the foundation for the AWS CLI as well as boto3"
SECTION = "devel/python"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=2ee41112a44fe7014dce33e26468ba93"

SRC_URI[md5sum] = "22482c26b54b56bab282d76400c49d3f"
SRC_URI[sha256sum] = "2a07533de92603607c8b594ff92647f5d5a39e75f66c9476ccd30ed4d6de37ae"

SRCNAME = "botocore"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native"

inherit pypi setuptools3
