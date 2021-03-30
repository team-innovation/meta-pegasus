DESCRIPTION = "Async http client/server framework"
SECTION = "devel/python"
PRIORITY = "optional"
LICENSE = "Apache-2.0"
SRCNAME = "aiodns"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=cf056e8e7a0a5477451af18b7b5aa98c"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native nativesdk"

inherit setuptools3 pypi 

SRC_URI[md5sum] = "ca40144c199a09fc1a141960cf6295f0"
SRC_URI[sha256sum] = "259ab809ff0727d0e834ac5e8a283dc5e3e0ecc30c4d80b3cd17a4139ce1f326"

