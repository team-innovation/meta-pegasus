SUMMARY = "Python bindings for the avahi zeroconf client"
HOMEPAGE = "https://github.com/lathiat/avahi"
SECTION = "devel/python"

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://LICENSE;md5=2d5025d4aa3495befef8f17206a5b0a1"

SRC_URI = "https://github.com/lathiat/avahi/releases/download/v${PV}/avahi-${PV}.tar.gz"

SRC_URI[md5sum] = "229c6aa30674fc43c202b22c5f8c2be7"
SRC_URI[sha256sum] = "060309d7a333d38d951bc27598c677af1796934dbd98e1024e7ad8de798fedda"

S = "${WORKDIR}/avahi-${PV}"

inherit pythonnative

# we only need the python bindings
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

BBCLASSEXTEND = "native nativesdk"
RDEPENDS_${PN} += "python3 python3-core python3-dbus"

FILES_${PN} = "${PYTHON_SITEPACKAGES_DIR}/avahi"

INSANE_SKIP_${PN} += "file-rdeps"
