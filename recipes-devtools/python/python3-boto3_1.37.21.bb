SUMMARY = "The AWS SDK for Python"
HOMEPAGE = "https://github.com/boto/boto3"
SECTION = "devel/python"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=2ee41112a44fe7014dce33e26468ba93"

SRC_URI[sha256sum] = "69227716293facaf80d78c6c5c7291bdcc4e2cb5f8e2d3e7258c1cbd071c9cc3"
S = "${WORKDIR}/boto3-${PV}"

inherit setuptools3 pypi