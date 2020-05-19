SUMMARY = "Internationalised Domain Names in Applications"
HOMEPAGE = "https://github.com/kjd/idna"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE.rst;md5=782775b32f96098512e283fb5d4546cd"

inherit pypi setuptools3

SRC_URI[md5sum] = "0e5bb69018ddef1b9d95f681182be82c"
SRC_URI[sha256sum] = "684a38a6f903c1d71d6d5fac066b58d7768af4de2b832e426ec79c30daa94a16"

RDEPENDS_${PN}_class-target = "\
    ${PYTHON_PN}-codecs \
"

BBCLASSEXTEND = "native nativesdk"
