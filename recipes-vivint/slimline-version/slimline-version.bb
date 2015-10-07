# Copyright (C) 2015 Vivint Innovation 

DESCRIPTION = "SlimLine Version Information"
SECTION = "slimline"
LICENSE = "CLOSED"
DEPENDS = ""
PV = "${DISTRO_VERSION}"
PR = "r0"

SRC_URI =   "file://lsb_release"

PACKAGES = "${PN}"
PACKAGE_ARCH = "${MACHINE_ARCH}"

do_install() {

    install -d ${D}${sysconfdir}
    echo "SlimLine ${DISTRO_VERSION}" > ${D}${sysconfdir}/slimline-version
    echo "$(date '+Build Date: %m/%d/%Y')" >> ${D}${sysconfdir}/slimline-version
    echo "Repo manifest: ${REPO_MANIFEST}" >> ${D}${sysconfdir}/slimline-version
    echo "OE branch: ${METADATA_BRANCH}" >> ${D}${sysconfdir}/slimline-version
    echo "OERev: ${METADATA_REVISION}" >> ${D}${sysconfdir}/slimline-version
    echo "AppsRev: ${HG_APPS_ID}" >> ${D}${sysconfdir}/slimline-version
    echo "Target system: ${TARGET_SYS}" >> ${D}${sysconfdir}/slimline-version

    install -d ${D}${bindir}
    install -m 0755 ${WORKDIR}/lsb_release ${D}${bindir}/

}
