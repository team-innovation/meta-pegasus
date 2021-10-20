
# openwrt build file
# Copyright (C) 2019, Vivint
#

DESCRIPTION = "Network Module Packages"
HOMEPAGE = "www.vivint.com"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${WORKDIR}/COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

GIT_ARTIFACTS_LATEST_BRANCH ?= "feature/18.06-staging"
GIT_ARTIFACTS_LATEST_SERVER ?= "${GIT_SERVER}"
GIT_ARTIFACTS_LATEST_PROTOCOL ?= "ssh"

GIT_ARTIFACTS_LATEST_REV ?= "${AUTOREV}"
SRCREV = "${GIT_ARTIFACTS_LATEST_REV}"

SRC_URI = "git://${GIT_ARTIFACTS_LATEST_SERVER}/artifacts;protocol=${GIT_ARTIFACTS_LATEST_PROTOCOL};branch=${GIT_ARTIFACTS_LATEST_BRANCH} \
	file://COPYING \
	"

PR = "r5"
PV = "${SRCPV}"

MT7620_update_pkg = "nm_pkgs.tar.gz"
REPO_DIR = "git/wallsly"

SRV_WWW_DIR = "/srv/www/network/latest"

do_configure[noexec] = "1"

do_compile[noexec] = "1"

do_install() {
    install -d ${D}${SRV_WWW_DIR}/packages
    tar --no-same-owner -xzf ${WORKDIR}/${REPO_DIR}/${MT7620_update_pkg} -C ${D}${SRV_WWW_DIR}/packages
}

FILES_${PN} = "\
    ${SRV_WWW_DIR}/packages/* \
    "

#Commenting the below for so that we can enable it in future.
pkg_postinst_${PN}() {
#!/bin/sh -e
# create symlink to /src/www/network for actual packages to use

if [ x"$D" = "x" ]; then
	( cd /srv/www/network ; ln -sf latest/* .)
else
	exit 1
fi
}

