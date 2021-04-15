DESCRIPTION = "Utility for simulating a throttled connection"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=50f39a7cfda1c242e10563d49e18f9d1" 
inherit autotools

SRC_URI += " \
    file://throttle-1.2.tar.gz \
"
S="${WORKDIR}/throttle-1.2"


