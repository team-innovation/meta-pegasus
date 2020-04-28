# Copyright (C) 2017 Vivint Innovation 

DESCRIPTION = "WallSly Version Information"
SECTION = "wallsly"
LICENSE = "CLOSED"
DEPENDS = ""
PV = "${DISTRO_VERSION}"
PR = "r5"

SRC_URI =	"file://lsb_release_wallsly \
		 file://issue_wallsly \
		 file://issue_wallsly.net \
		 file://brand.sh"

PACKAGES = "${PN}"
PACKAGE_ARCH = "${MACHINE_ARCH}"

RPROVIDES_${PN} = "wallsly-version"

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
	echo "${WALLSLY_NAME} ${DISTRO_VERSION}" > ${D}${sysconfdir}/wallsly-version
	echo "Cellular: A.01" >> ${D}${sysconfdir}/wallsly-version
	echo "$(date '+Build Date: %m/%d/%Y')" >> ${D}${sysconfdir}/wallsly-version
	echo "Repo manifest: ${REPO_MANIFEST}" >> ${D}${sysconfdir}/wallsly-version
	echo "OE branch: ${GIT_META_VIVINT_BRANCH%)}" >> ${D}${sysconfdir}/wallsly-version
	echo "OERev: ${GIT_META_VIVINT_REV}" >> ${D}${sysconfdir}/wallsly-version
	echo "AppsRev: ${APPS_REV}" >> ${D}${sysconfdir}/wallsly-version
	echo "Target system: ${TARGET_SYS}" >> ${D}${sysconfdir}/wallsly-version

	install -d ${D}${bindir}
	install -m 0755 ${WORKDIR}/lsb_release_wallsly ${D}${bindir}/

	# issue
	install -m 0644 ${WORKDIR}/issue_wallsly*  ${D}${sysconfdir}
	if [ -n "${WALLSLY_NAME}" ]; then
		printf "${WALLSLY_NAME} " >> ${D}${sysconfdir}/issue_wallsly
		printf "${WALLSLY_NAME} " >> ${D}${sysconfdir}/issue_wallsly.net
	fi
	if [ -n "${DISTRO_VERSION}" ]; then
		printf "${DISTRO_VERSION} " >> ${D}${sysconfdir}/issue_wallsly
		printf "${DISTRO_VERSION} " >> ${D}${sysconfdir}/issue_wallsly.net
	fi
	printf "\\\n \\\l\n" >> ${D}${sysconfdir}/issue_wallsly
	echo >> ${D}${sysconfdir}/issue_wallsly
	echo "%h"    >> ${D}${sysconfdir}/issue_wallsly.net
	echo >> ${D}${sysconfdir}/issue_wallsly.net

	install -d ${D}${sysconfdir}/init.d
	install -m 0755 ${WORKDIR}/brand.sh ${D}${sysconfdir}/init.d
}
