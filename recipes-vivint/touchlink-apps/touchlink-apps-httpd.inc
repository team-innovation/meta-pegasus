###
### Change HTTPD_PREPEND everything you touch this script
###
HTTPD_PREPEND = "-3"

HTTPD_REVISION ?= "${DISTRO_PR}"
PKGR_${PN}-httpd = "${PR}${HTTPD_PREPEND}${HTTPD_REVISION}"

RDEPENDS_${PN}-httpd = "\
    python3-zeroconf \
    python3-sseclient \
	python3-pyyaml \
	"

do_install_append () {
	install -d ${D}/${INSTALL_DIR}/httpd/
        ${RSYNC_CMD} ${S}/code/httpd/ ${D}/${INSTALL_DIR}/httpd/
        chmod +x ${D}/${INSTALL_DIR}/httpd/httpd.pyc
}

pkg_postinst_ontarget_${PN}-sundance (){
	#!/bin/sh -e
	# Post install to make sure we have the correct setup for httpd
	#
	platform=$(strings /proc/device-tree/compatible | grep vivint | sed s/^vivint,//)
	if [ "$platform" != "brazen" ]; then
		rm -f /etc/procman.d/httpd
	fi
}

FILES_${PN}-httpd = "\
	${INSTALL_DIR}/httpd/*   \
	${sysconfdir}/init.d/httpd \
	${sysconfdir}/procman.d/httpd \
       "

