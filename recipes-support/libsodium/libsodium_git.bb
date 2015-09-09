SUMMARY = "The Sodium crypto library"
HOMEPAGE = "http://libsodium.org/"
LICENSE = "ISC"
LIC_FILES_CHKSUM = "file://${WORKDIR}/libsodium-master/LICENSE;md5=092a09b78c3be486fac807435bf17b7a"

#SRC_URI = "https://download.libsodium.org/libsodium/releases/${BPN}-${PV}.tar.gz"
SRC_URI = "https://github.com/jedisct1/libsodium/archive/master.zip"

SRC_URI[md5sum] = "635a4acb07965ebf0202b7d888b71af2"
SRC_URI[sha256sum] = "dc3fb75850786b664cf7b9df4166279a5d0fd8c08ff863de86f3c560962d8e6f"

S = "${WORKDIR}/libsodium-master"

RPROVIDES_${PN} = "libsodium13"

inherit autotools
