DESCRIPTION = "Python sentry"
SECTION = "devel/python"
LICENSE = "BSD-2-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=0c79f8d3c91fc847350efd28bfe0a341"
PR = "r1"
SRCNAME = "sentry-sdk"

SRC_URI[md5sum] = "89360af1b022a662d33a626d05021ccd"
SRC_URI[sha256sum] = "71de00c9711926816f750bc0f57ef2abbcb1bfbdf5378c601df7ec978f44857a"

inherit pypi setuptools3 

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native nativesdk"


