###
### Change PROCMAND_PREPEND everything you touch this script
###
PROCMAND_PREPEND = "-4"

PROCMAND_REVISION ?= "${DISTRO_PR}"
PKGR_${PN}-procmand = "${PR}${PROCMAND_PREPEND}${PROCMAND_REVISION}"

RDEPENDS_${PN}-procmand = "\
	bash \
	python3-pyinotify \
	python3-psutil \
 	python3-cherrypy	\
	${PN}-favicon \
	bash \
	"

RREPLACES_${PN}-procmand = "monit"
RCONFLICTS_${PN}-procmand = "monit"


INITSCRIPT_PACKAGES += "${PN}-procmand"
INITSCRIPT_NAME_${PN}-procmand = "procmand"
INITSCRIPT_PARAMS_${PN}-procmand = "defaults 88 18"

do_install_append() {
	install -d ${D}/${INSTALL_DIR}/procmand/
        ${RSYNC_CMD} ${S}/code/procmand/ ${D}/${INSTALL_DIR}/procmand/
        chmod +x ${D}/${INSTALL_DIR}/procmand/procmand.pyc

	# Install procman startup
	install -d ${D}/${sysconfdir}/procman.d/
	install ${WORKDIR}/procman.d/* ${D}/${sysconfdir}/procman.d/
	install -d ${D}/${sysconfdir}/procman.d-fcc/
	install ${WORKDIR}/procman.d-fcc/* ${D}/${sysconfdir}/procman.d-fcc/

	install -d ${D}/usr/local/bin
	install ${S}/code/procmand/scripts/procman ${D}/usr/local/bin
	chmod 0755 ${D}/usr/local/bin/*
}

FILES_${PN}-procmand = "\
       ${INSTALL_DIR}/procmand/*   \
	${sysconfdir}/init.d/procmand		\
	${sysconfdir}/procman.d/syslogd		\
       /usr/local/bin/procman	\
       "

             
