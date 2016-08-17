DESCRIPTION = "A Pure Python Expect-u like Module for Python"
SECTION = "devel/python"
PRIORITY = "optional"
LICENSE = "PSF"
LIC_FILES_CHKSUM = "file://LICENSE;md5=04a2bf11b85ce49d4a8c0c413fd34404"
SRCNAME = "pexpect-u"
PR = "ml1"

SRC_URI = "https://pypi.python.org/packages/source/p/pexpect-u/${SRCNAME}-${PV}.tar.gz \
		   file://fixup_setuptool_to_distutils.patch"

S = "${WORKDIR}/${SRCNAME}-${PV}"

inherit pythonnative

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native"

NATIVE_INSTALL_WORKS = "1"

inherit distutils3

RDEPENDS_${PN} = "\
  python3-core \
  python3-io \
  python3-terminal \
  python3-resource \
  python3-fcntl \
"

RDEPENDS_${PN}_class-native = ""

do_compile_append() {
	2to3 -w -p ${S}/build
}

SRC_URI[md5sum] = "7c916a9f42d636ec6aec39f7cdd96eb5"
SRC_URI[sha256sum] = "ab24557ef52c1bc6ff79f81408d9357888dfa55a4d90657576822c24c36f077e"
