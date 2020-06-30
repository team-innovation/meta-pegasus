DESCRIPTION = "PyCharm remote debug Support for Python"
SECTION = "devel/python"
PRIORITY = "optional"
LICENSE = "PSF"
SRCNAME = "debug/debug_files"
PR = "ml2"
PV = "1.0+git${SRCPV}"

SRCREV = "${GIT_APPS_REV}"
SRCBRANCH = "${GIT_APPS_BRANCH}"
GIT_APPS_SERVER ?= "${GIT_SERVER}"
GIT_APPS_PROTOCOL ?= "ssh"
SRC_URI = "git://${GIT_APPS_SERVER}/${GIT_APPS_TAG};protocol=${GIT_APPS_PROTOCOL};branch=${SRCBRANCH}"

S = "${WORKDIR}/git/${SRCNAME}"

RSYNC_CMD = "rsync -azv --exclude=.svn --exclude=test --exclude=.coverage --exclude=_coverage --exclude=tools --exclude=_user_conf"

inherit autotools python-dir

DEPENDS += "python3 python3-native"
DEPENDS_virtclass-native += "python3-native"

RDEPENDS_${PN} = "python-core"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native nativesdk"

do_install_prepend() {
    install -d ${D}/opt/2gig/debug
    install -d ${D}/${libdir}/${PYTHON_DIR}/site-packages
}

do_install() {
     ${RSYNC_CMD} ${WORKDIR}/git/debug/debug.py ${D}/opt/2gig/debug/
     ${RSYNC_CMD} ${WORKDIR}/git/debug/README ${D}/opt/2gig/debug/
     ${RSYNC_CMD} ${S}/pydev.pth ${D}/${libdir}/${PYTHON_DIR}/site-packages/
     ${RSYNC_CMD} ${S}/pydev ${D}/${libdir}/${PYTHON_DIR}/site-packages/
}

do_compile[noexec] = "1"

do_configure[noexec] = "1"

do_compileconfigs[noexec] = "1"

do_setscene[noexec] = "1"

do_distribute_sources[noexec] = "1"

do_create_srcipk[noexec] = "1"

do_copy_license[noexec] = "1"

RDEPENDS_${PN} = "\
  python-fcntl \
  python-io \
  python-stringold \
"
FILES_${PN} = "\
  /opt/2gig/debug/* \
 /${libdir}/${PYTHON_DIR}/site-packages/* \
"
