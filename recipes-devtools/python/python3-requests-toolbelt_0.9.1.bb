DESCRIPTION = "A utility belt for advanced users of python-requests"
HOMEPAGE = "https://pypi.org/project/requests-toolbelt/"
SECTION = "devel/python"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=71760e0f1dda8cff91b0bc9246caf571"

PR = "r0"
SRCNAME = "requests-toolbelt-${PV}"

SRC_URI = "http://updateseng.vivint.com/innovation/downloads/requests-toolbelt-${PV}.tar.gz"

SRC_URI[md5sum] = "b1509735c4b4cf95df2619facbc3672e"
SRC_URI[sha256sum] = "968089d4584ad4ad7c171454f0a5c6dac23971e9472521ea3b6d49d610aa6fc0"

S = "${WORKDIR}/${SRCNAME}"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native nativesdk"

inherit setuptools3 python3-dir
