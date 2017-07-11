# Copyright (C) 2015 Vivint Innovation 

DESCRIPTION = "SkyHub Version Information"
SECTION = "skyhub"
LICENSE = "CLOSED"
DEPENDS = ""
PV = "${DISTRO_VERSION}"
PR = "r0"

SRC_URI =	"file://lsb_release \
		file://issue \
		file://issue.net"

PACKAGES = "${PN}"
PACKAGE_ARCH = "${MACHINE_ARCH}"

RPROVIDES_${PN} = "sly-version"

do_install() {
	# version
	install -d ${D}${sysconfdir}
	echo "${SKYHUB_NAME} ${DISTRO_VERSION}" > ${D}${sysconfdir}/version
	echo "${SKYHUB_NAME} ${DISTRO_VERSION}" > ${D}${sysconfdir}/sly-version
	echo "$(date '+Build Date: %m/%d/%Y')" >> ${D}${sysconfdir}/sly-version
	echo "Repo manifest: ${REPO_MANIFEST}" >> ${D}${sysconfdir}/sly-version
	echo "OE branch: ${METADATA_BRANCH}" >> ${D}${sysconfdir}/sly-version
	echo "OERev: ${METADATA_REVISION}" >> ${D}${sysconfdir}/sly-version
	echo "AppsRev: ${GIT_APPS_REV}" >> ${D}${sysconfdir}/sly-version
	echo "Target system: ${TARGET_SYS}" >> ${D}${sysconfdir}/sly-version

	install -d ${D}${bindir}
	install -m 0755 ${WORKDIR}/lsb_release ${D}${bindir}/

	# issue
	install -m 0644 ${WORKDIR}/issue*  ${D}${sysconfdir}
	if [ -n "${SKYHUB_NAME}" ]; then
		printf "${SKYHUB_NAME} " >> ${D}${sysconfdir}/issue
		printf "${SKYHUB_NAME} " >> ${D}${sysconfdir}/issue.net
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
