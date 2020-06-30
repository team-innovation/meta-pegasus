DESCRIPTION = "UBUS  for Python"
SECTION = "devel/python"
PRIORITY = "optional"
LICENSE = "LGPL-2.1"
LIC_FILES_CHKSUM = "\
   file://setup.py;md5=fa158e2571f6e84c10cdf99cbbacc1f5 \
"

inherit setuptools3 

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native nativesdk"

PR = "ml7"
PR_append = "+gitr${SRCREV}"

SRCREV = "58d914244ef47a7ba99b18222771ccdd0bb89b22"
SRC_URI = "git://github.com/rytilahti/python-ubus.git;protocol=https \
"
S = "${WORKDIR}/git"

