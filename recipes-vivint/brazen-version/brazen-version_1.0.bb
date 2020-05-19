# Copyright (C) 2020 Vivint Innovation 

DESCRIPTION = "Brazen Version Information"
SECTION = "brazen"
LICENSE = "CLOSED"
DEPENDS = ""
PV = "${DISTRO_VERSION}"
PR = "r2"

SRC_URI =	"file://lsb_release_brazen \
		 file://issue_brazen \
		 file://issue_brazen.net \
		 file://brand.sh"

PACKAGES = "${PN}"
PACKAGE_ARCH = "${MACHINE_ARCH}"

RPROVIDES_${PN} = "brazen-version"

SRCREV = "${GIT_APPS_REV}"
SRCBRANCH = "${GIT_APPS_BRANCH}"
inherit update-rc.d

INITSCRIPT_NAME = "brand.sh"
INITSCRIPT_PARAMS = "start 04 S ."

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
	echo "${BRAZEN_NAME} ${DISTRO_VERSION}" > ${D}${sysconfdir}/brazen-version
	echo "Cellular: A.01" >> ${D}${sysconfdir}/brazen-version
	echo "$(date '+Build Date: %m/%d/%Y')" >> ${D}${sysconfdir}/brazen-version
	echo "Repo manifest: ${REPO_MANIFEST}" >> ${D}${sysconfdir}/brazen-version
	echo "OE branch: ${GIT_META_VIVINT_BRANCH%)}" >> ${D}${sysconfdir}/brazen-version
	echo "OERev: ${GIT_META_VIVINT_REV}" >> ${D}${sysconfdir}/brazen-version
	echo "AppsRev: ${APPS_REV}" >> ${D}${sysconfdir}/brazen-version
	echo "Target system: ${TARGET_SYS}" >> ${D}${sysconfdir}/brazen-version

	install -d ${D}${bindir}
	install -m 0755 ${WORKDIR}/lsb_release_brazen ${D}${bindir}/

	# issue
	install -m 0644 ${WORKDIR}/issue_brazen*  ${D}${sysconfdir}
	if [ -n "${BRAZEN_NAME}" ]; then
		printf "${BRAZEN_NAME} " >> ${D}${sysconfdir}/issue_brazen
		printf "${BRAZEN_NAME} " >> ${D}${sysconfdir}/issue_brazen.net
	fi
	if [ -n "${DISTRO_VERSION}" ]; then
		printf "${DISTRO_VERSION} " >> ${D}${sysconfdir}/issue_brazen
		printf "${DISTRO_VERSION} " >> ${D}${sysconfdir}/issue_brazen.net
	fi
	printf "\\\n \\\l\n" >> ${D}${sysconfdir}/issue_brazen
	echo >> ${D}${sysconfdir}/issue_brazen
	echo "%h"    >> ${D}${sysconfdir}/issue_brazen.net
	echo >> ${D}${sysconfdir}/issue_brazen.net

	install -d ${D}${sysconfdir}/init.d
	install -m 0755 ${WORKDIR}/brand.sh ${D}${sysconfdir}/init.d
}
