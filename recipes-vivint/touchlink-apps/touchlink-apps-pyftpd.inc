###
### Change RTSPD_PREPEND everything you touch this script
###
PYFTPD_PREPEND = "-2"

PYFTPD_REVISION ?= "${DISTRO_PR}"
PKGR_${PN}-pyftpd = "${PR}${PYFTPD_PREPEND}${PYFTPD_REVISION}"

RDEPENDS_${PN}-pyftpd = "\
	python3-pyftpdlib \
"

do_install_append () {
	install -d ${D}/${INSTALL_DIR}/pyftpd/
	${RSYNC_CMD} ${S}/code/pyftpd/ ${D}/${INSTALL_DIR}/pyftpd/
	chmod +x ${D}/${INSTALL_DIR}/pyftpd/pyftpd.pyc
}


pkg_postinst_ontarget_${PN}-pyftpd() {
#!/bin/sh
# Post install to setup the home id directories
#
set -e

 logging()
 {
    if busybox ps | grep psplash | grep -qv grep
    then
        psplash-write "MSG $*"
    fi
    echo $*
 }

if [ ! -d /media/clips/ftp ]
then
     logging "Creating /media/clips/ftp..."
     mkdir -p /media/clips/ftp
fi
}

FILES_${PN}-pyftpd = "\
       ${INSTALL_DIR}/pyftpd/*   \
	${sysconfdir}/init.d/pyftpd \
	${sysconfdir}/procman.d/pyftpd \
   "
