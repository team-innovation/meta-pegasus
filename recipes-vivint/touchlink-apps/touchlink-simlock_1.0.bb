DESCRIPTION = "Touchlink Sim Lock"
LICENSE = "CLOSED"
HOMEPAGE = "http://www.vivint.com"

PR = "ml2"
SIMLOCK_REVISION ?= "${DISTRO_PR}"
PKGR_${PN} = "${PR}${SIMLOCK_REVISION}"


MODULE = "lock"
2GIG_DIR = "opt/2gig"
S = "${WORKDIR}/git/code/SimLock"

GIT_SIMLOCK_SERVER ?= "${GIT_SERVER}"
GIT_SIMLOCK_REV ?= "${AUTOREV}"
GIT_SIMLOCK_PROTOCOL ?= "ssh"
GIT_SIMLOCK_BRANCH ?= "master"

SRCREV = "${GIT_SIMLOCK_REV}"
SRC_URI = "git://${GIT_SIMLOCK_SERVER}/${MODULE};protocol=${GIT_SIMLOCK_PROTOCOL};branch=${GIT_SIMLOCK_SERVER}"


PACKAGE_ARCH = "${MACHINE_ARCH}"

FILES_${PN} = "\
	/opt/2gig/lib/libsimLock.so \
"
FILES_${PN}-dbg += "\
	/opt/2gig/lib/.debug/ \
"

do_compile() {
 	oe_runmake CFLAGS+="-shared -fPIC -I../../boost_1_53_0 -lstdc++"
}


# TODO - For now just skip this, need to fix to only skip the firmware files
do_package_qa(){
	:
}

do_install() {
	install -d ${D}/${2GIG_DIR}/lib/
	# TODO - I think we should put firmware into it's own directory or with the lib (will require changes to the issserver lib)
	install ${S}/lib*.so ${D}/${2GIG_DIR}/lib/
}

