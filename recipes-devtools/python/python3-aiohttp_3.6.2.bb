DESCRIPTION = "Async http client/server framework"
SECTION = "devel/python"
PRIORITY = "optional"
LICENSE = "Apache-2.0"
SRCNAME = "aiodns"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=cf056e8e7a0a5477451af18b7b5aa98c"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native nativesdk"

inherit setuptools3 pypi 

SRC_URI[md5sum] = "85fe5c9037256c58d4678148bd91b3f3"
SRC_URI[sha256sum] = "9c4c83f4fa1938377da32bc2d59379025ceeee8e24b89f72fcbccd8ca22dc9bf"

