DESCRIPTION = "Python wrapper for libsodium"
SECTION = "devel/python"
LICENSE = "BSD"

PR = "r1"

SRC_URI = "https://github.com/stef/pysodium/archive/v${PV}.zip \
"
SRC_URI[md5sum] = "17319bf9c4336fdde6c99329c409df60"
SRC_URI[sha256sum] = "f9d9d6ef8b271121d731c9a450a989adefdb513000086debc468e15105c7abc7"
LIC_FILES_CHKSUM = "file://README.md;md5=f08e175d67ce47a2ea50bf12c7b60998"

S = "${WORKDIR}/pysodium-${PV}"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native"

NATIVE_INSTALL_WORKS = "1"

RDEPENDS_${PN} = "libsodium"

inherit setuptools3 python3-dir
