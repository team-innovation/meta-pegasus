
# openwrt build file
# Copyright (C) 2019, Vivint
#

DESCRIPTION = "Network Module Packages"
HOMEPAGE = "www.vivint.com"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://../COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

GIT_ARTIFACTS_BRANCH ?= "develop"
GIT_ARTIFACTS_SERVER ?= "${GIT_SERVER}"
GIT_ARTIFACTS_PROTOCOL ?= "ssh"

GIT_ARTIFACTS_REV ?= "${AUTOREV}"
SRCREV = "${GIT_ARTIFACTS_REV}"

SRC_URI_append = "git://${GIT_ARTIFACTS_SERVER}/artifacts;protocol=${GIT_ARTIFACTS_PROTOCOL};branch=${GIT_ARTIFACTS_BRANCH} \
                  file://COPYING"

PR = "r1"
PV = "${SRCPV}"

MT7620_update_pkg = "nm_pkgs.tar.gz"
REPO_DIR = "git/wallsly"

SRV_WWW_DIR = "/srv/www/network"

do_configure[noexec] = "1"

do_compile[noexec] = "1"

do_install() {
    install -d ${D}${SRV_WWW_DIR}/packages
    tar xzf ${WORKDIR}/${REPO_DIR}/${MT7620_update_pkg} -C ${D}${SRV_WWW_DIR}/packages
}

FILES_${PN} = "\
    ${SRV_WWW_DIR}/packages/* \
    "
