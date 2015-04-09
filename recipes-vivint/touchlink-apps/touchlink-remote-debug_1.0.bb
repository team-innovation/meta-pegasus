DESCRIPTION = "PyCharm remote debug Support for Python"
SECTION = "devel/python"
PRIORITY = "optional"
LICENSE = "PSF"
SRCNAME = "debug/debug_files"
PR = "ml1"

SRCREV = "${HG_APPS_ID}"
SRC_URI = "hg://${HG_SERVER};module=${HG_APPS_TAG};proto=http"

S = "${WORKDIR}/apps-hg/${SRCNAME}"

RSYNC_CMD = "rsync -azv --exclude=.svn --exclude=test --exclude=.coverage --exclude=_coverage --exclude=tools --exclude=_user_conf"

inherit autotools python-dir

DEPENDS += "python python-native"
DEPENDS_virtclass-native += "python-native"

RDEPENDS_${PN} = "python-core"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native"

NATIVE_INSTALL_WORKS = "1"

do_install_prepend() {
    install -d ${D}/opt/2gig/debug
    install -d ${D}/${libdir}/${PYTHON_DIR}/site-packages
}

do_install() {
     ${RSYNC_CMD} ${WORKDIR}/apps-hg/debug/debug.py ${D}/opt/2gig/debug/
     ${RSYNC_CMD} ${WORKDIR}/apps-hg/debug/README ${D}/opt/2gig/debug/
     ${RSYNC_CMD} ${S}/pydev.pth ${D}/${libdir}/${PYTHON_DIR}/site-packages/
     ${RSYNC_CMD} ${S}/pydev ${D}/${libdir}/${PYTHON_DIR}/site-packages/
}

do_compile() {
     :
}

do_configure() {
     :
}

do_compileconfigs() {
     :
}

do_setscene() {
     :
}

do_distribute_sources() {
     :
}

do_create_srcipk() {
     :
}

do_copy_license() {
     :
}

RDEPENDS_${PN} = "\
  python-fcntl \
  python-io \
  python-stringold \
"
FILES_${PN} = "\
  /opt/2gig/debug/* \
 /${libdir}/${PYTHON_DIR}/site-packages/* \
"
