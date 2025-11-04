SUMMARY = "Official Mixpanel library for Python"
HOMEPAGE = "https://github.com/mixpanel/mixpanel-python"
SECTION = "devel/python"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=793aaeb67c2420fa8401fefdabe8f122"

SRC_URI[sha256sum] = "29a6b5773dd34f05cf8e249f4e1d16e7b6280d6b58894551ce9a5aad7700a115"

inherit setuptools3 pypi

BBCLASSEXTEND = "native nativesdk"
