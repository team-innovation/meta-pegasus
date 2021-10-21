HOMEPAGE = "https://github.com/boto/botocore"
DESCRIPTION = "Botocore is the Amazon Web Services (AWS) low-level interface to a number of services, but for our purposes: the foundation for the AWS CLI as well as boto3"
SECTION = "devel/python"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=2ee41112a44fe7014dce33e26468ba93"

SRCNAME = "botocore"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native"

inherit pypi setuptools3
