# Copyright (C) 2015 Vivint Innovation

DESCRIPTION = "Fake Touchlink Version Information"
SECTION = "touchlink"
LICENSE = "CLOSED"
DEPENDS = ""
PV = "${DISTRO_VERSION}"
PR = "r1"

PACKAGES = "${PN}"
PACKAGE_ARCH = "${MACHINE_ARCH}"

SRCREV = "${GIT_APPS_REV}"
SRCBRANCH = "${GIT_APPS_BRANCH}"

GIT_APPS_SERVER ?= "${GIT_SERVER}"
GIT_APPS_PROTOCOL ?= "ssh"
GIT_STRINGS_SERVER ?= "/home/localRepos/constants/boilerplate/python"

SRC_URI += "git://${GIT_APPS_SERVER}/${GIT_APPS_TAG};protocol=${GIT_APPS_PROTOCOL};branch=${SRCBRANCH}" 

do_compile[noexec] = "1"

do_install() {
	GIT_META_VIVINT_BRANCH=${GIT_META_VIVINT_BRANCH:=$(git -C ${TOPDIR}/../sources/meta-vivint show -s --pretty=%d HEAD)}
	GIT_META_VIVINT_BRANCH=${GIT_META_VIVINT_BRANCH##*, }
	GIT_META_VIVINT_REV=${GIT_META_VIVINT_REV:=$(git -C ${TOPDIR}/../sources/meta-vivint rev-parse HEAD)}    
	APPS_REV=${SRCPV}
	APPS_REV=${APPS_REV#AUTOINC\+}	
	install -d ${D}${sysconfdir}
	echo "Touchlink 3.3.0.17146" > ${D}${sysconfdir}/touchlink-version
	echo "Cellular: A.01" >> ${D}${sysconfdir}/touchlink-version
	echo "$(date '+Build Date: %m/%d/%Y')" >> ${D}${sysconfdir}/touchlink-version
	echo "Repo manifest: ${REPO_MANIFEST}" >> ${D}${sysconfdir}/touchlink-version
	echo "OE branch: ${GIT_META_VIVINT_BRANCH%)}" >> ${D}${sysconfdir}/touchlink-version
	echo "OERev: ${GIT_META_VIVINT_REV}" >> ${D}${sysconfdir}/touchlink-version
	echo "AppsRev: ${APPS_REV}" >> ${D}${sysconfdir}/wallsly-version	
	echo "Target system: ${TARGET_SYS}" >> ${D}${sysconfdir}/touchlink-version
}
