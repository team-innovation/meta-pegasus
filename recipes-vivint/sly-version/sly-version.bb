# Copyright (C) 2015 Vivint Innovation 

DESCRIPTION = "SkyHub Version Information"
SECTION = "skyhub"
LICENSE = "CLOSED"
DEPENDS = ""
PV = "${DISTRO_VERSION}"
PR = "r0"

SRC_URI =   "file://lsb_release"

PACKAGES = "${PN}"
PACKAGE_ARCH = "${MACHINE_ARCH}"

do_install() {

    install -d ${D}${sysconfdir}
    echo "SkyHub ${DISTRO_VERSION}" > ${D}${sysconfdir}/sly-version
    echo "$(date '+Build Date: %m/%d/%Y')" >> ${D}${sysconfdir}/sly-version
    echo "Repo manifest: ${REPO_MANIFEST}" >> ${D}${sysconfdir}/sly-version
    echo "OE branch: ${METADATA_BRANCH}" >> ${D}${sysconfdir}/sly-version
    echo "OERev: ${METADATA_REVISION}" >> ${D}${sysconfdir}/sly-version
    echo "AppsRev: ${HG_APPS_ID}" >> ${D}${sysconfdir}/sly-version
    echo "Target system: ${TARGET_SYS}" >> ${D}${sysconfdir}/sly-version

    install -d ${D}${bindir}
    install -m 0755 ${WORKDIR}/lsb_release ${D}${bindir}/

}
