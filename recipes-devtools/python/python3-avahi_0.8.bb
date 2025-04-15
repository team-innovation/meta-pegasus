SUMMARY = "Avahi - Service Discovery for Linux using mDNS/DNS-SD -- compatible with Bonjour"
HOMEPAGE = "https://github.com/avahi/avahi"
SECTION = "devel/python"

LICENSE = "LGPL-2.1"
LIC_FILES_CHKSUM = "file://LICENSE;md5=2d5025d4aa3495befef8f17206a5b0a1"

SRC_URI = "https://github.com/avahi/avahi/releases/download/v${PV}/avahi-${PV}.tar.gz"
SRC_URI[sha256sum] = "060309d7a333d38d951bc27598c677af1796934dbd98e1024e7ad8de798fedda"
S = "${WORKDIR}/avahi-${PV}"

inherit python3-dir

do_install () {
	install -d ${D}${PYTHON_SITEPACKAGES_DIR}/avahi

	sed -i'' -e "s,@PYTHON\@,${bindir}/${PYTHON_PN},g" \
		${S}/avahi-python/avahi/__init__.py  \
		${S}/avahi-python/avahi-bookmarks.in

	install -m 0775 ${S}/avahi-python/avahi/__init__.py \
		${D}${PYTHON_SITEPACKAGES_DIR}/avahi/__init__.py

	install -m 0775 ${S}/avahi-python/avahi-bookmarks.in \
		${D}${PYTHON_SITEPACKAGES_DIR}/avahi/avahi-bookmarks
}

RDEPENDS:${PN} += "\
    python3 \ 
    python3-core \
    python3-dbus \
"

FILES:${PN} = "${PYTHON_SITEPACKAGES_DIR}/avahi"

INSANE_SKIP:${PN} += "file-rdeps"