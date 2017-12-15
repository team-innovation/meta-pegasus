# Copyright (C) 2015 Vivint Innovation 

DESCRIPTION = "Glance Version Information"
SECTION = "slimline"
LICENSE = "CLOSED"
DEPENDS = ""
PV = "${DISTRO_VERSION}"
PR = "r2"

SRC_URI =	"file://lsb_release \
		file://issue \
		file://issue.net"

PACKAGES = "${PN}"
PACKAGE_ARCH = "${MACHINE_ARCH}"

RPROVIDES_${PN} = "slimline-version"

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
	# version
	install -d ${D}${sysconfdir}
	echo "${GLANCE_NAME} ${DISTRO_VERSION}" > ${D}${sysconfdir}/version
	echo "${GLANCE_NAME} ${DISTRO_VERSION}" > ${D}${sysconfdir}/slimline-version
	echo "$(date '+Build Date: %m/%d/%Y')" >> ${D}${sysconfdir}/slimline-version
	echo "Repo manifest: ${REPO_MANIFEST}" >> ${D}${sysconfdir}/slimline-version
	echo "OE branch: ${GIT_META_VIVINT_BRANCH%)}" >> ${D}${sysconfdir}/slimline-version
	echo "OERev: ${GIT_META_VIVINT_REV}" >> ${D}${sysconfdir}/slimline-version
	echo "AppsRev: ${APPS_REV}" >> ${D}${sysconfdir}/slimline-version	
	echo "Target system: ${TARGET_SYS}" >> ${D}${sysconfdir}/slimline-version

	install -d ${D}${bindir}
	install -m 0755 ${WORKDIR}/lsb_release ${D}${bindir}/

	# issue
	install -m 0644 ${WORKDIR}/issue*  ${D}${sysconfdir}
	if [ -n "${GLANCE_NAME}" ]; then
		printf "${GLANCE_NAME} " >> ${D}${sysconfdir}/issue
		printf "${GLANCE_NAME} " >> ${D}${sysconfdir}/issue.net
	fi
	if [ -n "${DISTRO_VERSION}" ]; then
		printf "${DISTRO_VERSION} " >> ${D}${sysconfdir}/issue
		printf "${DISTRO_VERSION} " >> ${D}${sysconfdir}/issue.net
	fi
	printf "\\\n \\\l\n" >> ${D}${sysconfdir}/issue
	echo >> ${D}${sysconfdir}/issue
	echo "%h"    >> ${D}${sysconfdir}/issue.net
	echo >> ${D}${sysconfdir}/issue.net
}
