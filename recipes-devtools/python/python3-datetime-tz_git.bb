DESCRIPTION = "Drop-in replacement for Python datetime which deeply cares about timezones"
SECTION = "devel/python"
PRIORITY = "optional"
DEPENDS = "autoconf"
SRCNAME = "python-datetime-tz"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"
LICENSE = "Apache-2.0"
PR = "m1"

DEPENDS += "python3 python3-pytz python3-dateutil"
DEPENDS_virtclass-native += "python3-native"

SRCREV = "ed74538f0a3adfb2898721e66b34ea9463f5cfc8"
PV = "1.0.0rc1+gitr${SRCREV}"
SRC_URI = "git://github.com/mithro/python-datetime-tz.git;protocol=git;branch=master"
S = "${WORKDIR}/git"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native"

NATIVE_INSTALL_WORKS = "1"

inherit distutils3

do_compile_append() {
#	oe_runmake CC="${CC}" CXX="${CXX}" LINK="${CXX}"
	2to3 -n -w ${S}/build/
	cp -r ${S}/build/* ${S}/
}

do_install_append() {
   rm -f ${D}/${libdir}/${PYTHON_DIR}/site-packages/__pycache__/site.cpython-33.pyc
   rm -f ${D}/${libdir}/${PYTHON_DIR}/site-packages/site.py
}

DEPENDS_${PN} = "\
  python3-distutils \
"
