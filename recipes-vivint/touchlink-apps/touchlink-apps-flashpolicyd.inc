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
	install -m 0755 ${S}/code/flashpolicyd/Python_init/flashpolicyd.init ${D}/etc/init.d/flashpolicyd
	install -m 0644 ${S}/code/flashpolicyd/flashpolicy.xml ${D}/etc/

	install -d ${D}/usr/sbin
	install -m 0755 ${S}/code/flashpolicyd/flashpolicyd.py ${D}/usr/sbin
}

FILES_${PN}-flashpolicyd = "\
       ${sysconfdir}/flashpolicy.xml   \
	${sysconfdir}/init.d/flashpolicyd \
       /usr/sbin/flashpolicyd.py	\
       "

             
