SUMMARY = "The Sodium crypto library"
HOMEPAGE = "http://libsodium.org/"
LICENSE = "ISC"
LIC_FILES_CHKSUM = "file://${WORKDIR}/git/LICENSE;md5=092a09b78c3be486fac807435bf17b7a"
PR = "r2"

#SRC_URI = "https://download.libsodium.org/libsodium/releases/${BPN}-${PV}.tar.gz"
SRCREV = "342f209cbe1c1c05330b832abf5b6d39f698e685"
SRC_URI = "git://github.com/jedisct1/libsodium.git"

S = "${WORKDIR}/git"

# http://www.embeddedlinux.org.cn/OEManual/chapter_reference.html#autotools_class
# Additional options to pass to configure:
#EXTRA_OECONF = "--disable-ssp"
# Additional make options:
#EXTRA_OEMAKE =

RPROVIDES_${PN} = "libsodium18"

inherit autotools
