# Copyright (C) 2017 Vivint Innovation 

DESCRIPTION = "WallSly Version Information"
SECTION = "wallsly"
LICENSE = "CLOSED"
DEPENDS = ""
PR = "r4"

SRC_URI =	"file://lsb_release-wallsly \
		 file://issue_wallsly \
		 file://issue_wallsly.net \
		 file://brand.sh"

PACKAGES = "${PN}"
PACKAGE_ARCH = "${MACHINE_ARCH}"

RPROVIDES_${PN} = "wallsly-version"

inherit update-rc.d

INITSCRIPT_NAME = "brand.sh"
INITSCRIPT_PARAMS = "start 04 S ."

do_install() {
	install -d ${D}${sysconfdir}

	if [ "${hostname}" ]; then
		echo ${hostname} > ${D}${sysconfdir}/hostname
	fi       

	# version
	echo "${WALLSLY_NAME} ${DISTRO_VERSION}" > ${D}${sysconfdir}/wallsly-version
    echo "Cellular: A.01" >> ${D}${sysconfdir}/wallsly-version
	echo "$(date '+Build Date: %m/%d/%Y')" >> ${D}${sysconfdir}/wallsly-version
	echo "Repo manifest: ${REPO_MANIFEST}" >> ${D}${sysconfdir}/wallsly-version
	echo "OE branch: ${METADATA_BRANCH}" >> ${D}${sysconfdir}/wallsly-version
	echo "OERev: ${METADATA_REVISION}" >> ${D}${sysconfdir}/wallsly-version
	echo "AppsRev: ${GIT_APPS_REV}" >> ${D}${sysconfdir}/wallsly-version
	echo "Target system: ${TARGET_SYS}" >> ${D}${sysconfdir}/wallsly-version

	install -d ${D}${bindir}
	install -m 0755 ${WORKDIR}/lsb_release-wallsly ${D}${bindir}/

	# issue
	install -m 0644 ${WORKDIR}/issue_wallsly*  ${D}${sysconfdir}
	if [ -n "${WALLSLY_NAME}" ]; then
		printf "${WALLSLY_NAME} " >> ${D}${sysconfdir}/issue_wallsly
		printf "${WALLSLY_NAME} " >> ${D}${sysconfdir}/issue_wallsly.net
	fi
	printf "\\\n \\\l\n" >> ${D}${sysconfdir}/issue_wallsly
	echo >> ${D}${sysconfdir}/issue_wallsly
	echo "%h"    >> ${D}${sysconfdir}/issue_wallsly.net
	echo >> ${D}${sysconfdir}/issue_wallsly.net

	install -d ${D}${sysconfdir}/init.d
	install -m 0755 ${WORKDIR}/brand.sh ${D}${sysconfdir}/init.d
}

