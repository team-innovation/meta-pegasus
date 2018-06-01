DESCRIPTION = "Mixpanel client library for Python"
SECTION = "devel/python"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=abc7b36b717e6a03d5cdb3d751c1e888"

PR = "r1"
SRCNAME = "mixpanel-python-master"

SRC_URI = "http://updateseng.vivint.com/innovation/downloads/mixpanel-python-master.zip \
	   "

SRC_URI[md5sum] = "0b24b1b621b47ec2bab109d2853fbbb9"
SRC_URI[sha256sum] = "bbc353443a346ad3270216b83ef357b25f278c185206d5d35e8cf838ce89444a"

S = "${WORKDIR}/${SRCNAME}"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native nativesdk"

NATIVE_INSTALL_WORKS = "1"

inherit setuptools3 python3-dir
