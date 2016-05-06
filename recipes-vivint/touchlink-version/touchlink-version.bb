# Copyright (C) 2015 Vivint Innovation

DESCRIPTION = "Fake Touchlink Version Information"
SECTION = "touchlink"
LICENSE = "CLOSED"
DEPENDS = ""
PV = "${DISTRO_VERSION}"
PR = "r0"

PACKAGES = "${PN}"
PACKAGE_ARCH = "${MACHINE_ARCH}"

do_install() {
    install -d ${D}${sysconfdir}
    echo "Touchlink 3.3.0.17050" > ${D}${sysconfdir}/touchlink-version
    echo "Cellular: A.01" >> ${D}${sysconfdir}/touchlink-version
    echo "$(date '+Build Date: %m/%d/%Y')" >> ${D}${sysconfdir}/touchlink-version
    echo "Repo manifest: ${REPO_MANIFEST}" >> ${D}${sysconfdir}/touchlink-version
    echo "OE branch: ${METADATA_BRANCH}" >> ${D}${sysconfdir}/touchlink-version
    echo "OERev: ${METADATA_REVISION}" >> ${D}${sysconfdir}/touchlink-version
    echo "AppsRev: ${HG_APPS_ID}" >> ${D}${sysconfdir}/touchlink-version
    echo "Target system: ${TARGET_SYS}" >> ${D}${sysconfdir}/touchlink-version
}
