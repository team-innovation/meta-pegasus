# Copyright (C) 2015 Vivint Innovation 

DESCRIPTION = "Glance Version Information"
SECTION = "slimline"
LICENSE = "CLOSED"
DEPENDS = ""
PV = "${DISTRO_VERSION}"
PR = "r1"

SRC_URI =	"file://lsb_release \
		file://issue \
		file://issue.net"

PACKAGES = "${PN}"
PACKAGE_ARCH = "${MACHINE_ARCH}"

RPROVIDES_${PN} = "slimline-version"

do_install() {
	# version
	install -d ${D}${sysconfdir}
	echo "Glance ${DISTRO_VERSION}" > ${D}${sysconfdir}/slimline-version
	echo "$(date '+Build Date: %m/%d/%Y')" >> ${D}${sysconfdir}/slimline-version
	echo "Repo manifest: ${REPO_MANIFEST}" >> ${D}${sysconfdir}/slimline-version
	echo "OE branch: ${METADATA_BRANCH}" >> ${D}${sysconfdir}/slimline-version
	echo "OERev: ${METADATA_REVISION}" >> ${D}${sysconfdir}/slimline-version
	echo "AppsRev: ${HG_APPS_ID}" >> ${D}${sysconfdir}/slimline-version
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
