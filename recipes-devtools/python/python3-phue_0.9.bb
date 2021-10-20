DESCRIPTION = "Python interface to Phillips Hue bridge"
SECTION = "devel/python"
LICENSE = "MIT"
SRCNAME = "phue"
LIC_FILES_CHKSUM = "file://LICENSE;md5=852ebbd5fd0880fc619e859b04cf2b6c"
PR = "r2"

SRCREV = "fd689cea50264a1af26c749e937e230cac732928"
PV = "1.0.0rc1+gitr${SRCREV}"
SRC_URI = "git://github.com/studioimaginaire/phue.git;protocol=https;branch=master \
          file://fix_device_type.patch"
S = "${WORKDIR}/git"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native nativesdk"

inherit setuptools3 

