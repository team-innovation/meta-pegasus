DESCRIPTION = "Touchlink Sim Lock"
LICENSE = "CLOSED"
HOMEPAGE = "http://www.vivint.com"

PR = "ml2"
SIMLOCK_REVISION ?= "${DISTRO_PR}"
PKGR_${PN} = "${PR}${SIMLOCK_REVISION}"


MODULE = "lock"
2GIG_DIR = "opt/2gig"
S = "${WORKDIR}/git/code/SimLock"

GIT_SIMLOCK_REV ?= "${AUTOREV}"
SRCREV = "${GIT_SIMLOCK_REV}"
SRC_URI = "git://git@${GIT_SERVER}/${MODULE};protocol=ssh"

PACKAGE_ARCH = "${MACHINE_ARCH}"
TARGET_CC_ARCH += "${LDFLAGS}"

FILES_${PN} = "\
	/opt/2gig/lib/libsimLock.so \
"
FILES_${PN}-dbg += "\
	/opt/2gig/lib/.debug/ \
"

do_configure() {
    sed -i '/^CC/d' ${S}/Makefile    
    sed -i '/^CFLAGS/d' ${S}/Makefile    
}

do_compile() {
 	oe_runmake CFLAGS+="-shared -fPIC -I../../boost_1_53_0 -lstdc++"
}

do_install() {
	install -d ${D}/${2GIG_DIR}/lib/
	# TODO - I think we should put firmware into it's own directory or with the lib (will require changes to the issserver lib)
	install ${S}/lib*.so ${D}/${2GIG_DIR}/lib/
}

