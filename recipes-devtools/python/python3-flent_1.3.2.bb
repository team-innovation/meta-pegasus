SUMMARY = "Flent FLExible network tool"
DESCRIPTION = "Flent FLExible network tool for network testing"
HOMEPAGE = "https://flent.org"
SECTION = "devel/python"
LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://LICENSE;md5=d32239bcb673463ab874e80d47fae504"

SRC_URI[md5sum] = "202475c65a78d39d58f106b3075905af"
SRC_URI[sha256sum] = "43e04e8388242e69c1fb09ee4454ba181c09eef14fd354d1c0a651d97b2f46cc"

PYPI_PACKAGE = "flent"
inherit setuptools3 pypi

RDEPENDS_${PN} = " \
    ${PYTHON_PN}-xmlrpc \
    iperf2 \                                                                                                                          
    netperf \                                                                                                                        
    fping  \
    bash \
"  

FILES_${PN} += "${datadir}/*"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native nativesdk"
