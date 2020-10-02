
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

SRC_URI_append = "git://${GIT_ARTIFACTS_SERVER}/artifacts;protocol=${GIT_ARTIFACTS_PROTOCOL};branch=${GIT_ARTIFACTS_BRANCH}"

PR = "r3"
PV = "${SRCPV}"

MT7620_update_pkg = "nm_pkgs.tar.gz"
REPO_DIR = "git/wallsly"

SRV_WWW_DIR = "/srv/www/network/legacy"

do_configure[noexec] = "1"

do_compile[noexec] = "1"

do_install() {
    install -d ${D}${SRV_WWW_DIR}/packages
    tar xzf ${WORKDIR}/${REPO_DIR}/${MT7620_update_pkg} -C ${D}${SRV_WWW_DIR}/packages
}

#Default will be legacy(17.01) for now.
#
pkg_postinst_${PN}() {
#!/bin/sh -e
# create symlink to /src/www/network for actual packages to use

if [ x"$D" = "x" ]; then
        ( cd /srv/www/network ; ln -sf legacy/* .)
else
        exit 1
fi
}

FILES_${PN} = "\
    ${SRV_WWW_DIR}/packages/* \
    "
