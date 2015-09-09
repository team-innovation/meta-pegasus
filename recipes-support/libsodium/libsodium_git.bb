SUMMARY = "The Sodium crypto library"
HOMEPAGE = "http://libsodium.org/"
LICENSE = "ISC"
LIC_FILES_CHKSUM = "file://${WORKDIR}/git/LICENSE;md5=092a09b78c3be486fac807435bf17b7a"

#SRC_URI = "https://download.libsodium.org/libsodium/releases/${BPN}-${PV}.tar.gz"
SRCREV = "7fa840e4867873f31acfe97125626ffd911c90de"
SRC_URI = "git://github.com/jedisct1/libsodium.git"

S = "${WORKDIR}/git"

RPROVIDES_${PN} = "libsodium13"

inherit autotools
