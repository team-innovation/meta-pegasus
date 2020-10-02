###
### Change FLASHPOLICYD_PREPEND everything you touch this script
###
FLASHPOLICYD_PREPEND = "-1"

FLASHPOLICYD_REVISION ?= "${DISTRO_PR}"
PKGR_${PN}-flashpolicyd = "${PR}${FLASHPOLICYD_PREPEND}${FLASHPOLICYD_REVISION}"

RDEPENDS_${PN}-flashpolicyd = "\
	"

INITSCRIPT_PACKAGES += "${PN}-flashpolicyd"

INITSCRIPT_NAME_${PN}-flashpolicyd = "flashpolicyd"
INITSCRIPT_PARAMS_${PN}-flashpolicyd = "defaults 99 18"

do_package_qa[noexec] = "1"

do_install_append() {
	install -d ${D}/etc/init.d/
	cp ${S}/code/flashpolicyd/Python_init/flashpolicyd.init ${D}/etc/init.d/flashpolicyd
	chmod 0755 ${D}/etc/init.d/flashpolicyd
	cp ${S}/code/flashpolicyd/flashpolicy.xml ${D}/etc/
	chmod 0644 ${D}/etc/flashpolicy.xml

	install -d ${D}/usr/sbin
	cp -a ${S}/code/flashpolicyd/flashpolicyd.py ${D}/usr/sbin
	chmod 0755 ${D}/usr/sbin/flashpolicyd.py
}

FILES_${PN}-flashpolicyd = "\
       ${sysconfdir}/flashpolicy.xml   \
	${sysconfdir}/init.d/flashpolicyd \
       /usr/sbin/flashpolicyd.py	\
       "

             
