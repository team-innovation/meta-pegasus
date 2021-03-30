DESCRIPTION = "Patch ssl.match_hostname for Unicode(idna) domains support"
HOMEPAGE = "https://pypi.org/project/idna_ssl/"
SECTION = "devel/python"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=a61b9c5aec8796b64a6bf15d42605073"
inherit pypi setuptools3

SRC_URI[md5sum] = "a7fc74e9530f0494cb75ca6486771832"
SRC_URI[sha256sum] = "1293f030bc608e9aa9cdee72aa93c1521bbb9c7698068c61c9ada6772162b979"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native nativesdk"

