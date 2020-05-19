SUMMARY = "Transitions - A lightweight, object-oriented finite state machine implementation"
HOMEPAGE = "https://github.com/pytransitions/transitions"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=5684c782127ebb08255dccae4001a2ce"

PR = "r1"
SRCNAME = "transitions-${PV}"

SRC_URI = "https://github.com/pytransitions/transitions/archive/${PV}.tar.gz"

SRC_URI[md5sum] = "d4bda5bc0529baeffbb9f117c8da5d0a"
SRC_URI[sha256sum] = "22ca9e2a204a44bffe0428d9fe5d0c1c0afe763993ff4e8742fbcec73faa467a"

S = "${WORKDIR}/${SRCNAME}"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native"

inherit setuptools3 
