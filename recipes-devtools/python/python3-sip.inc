DESCRIPTION = "Runtime helper for sip-generated python wrapper libraries"
SECTION = "devel/python"
HOMEPAGE = "http://www.riverbankcomputing.co.uk/sip"
AUTHOR = "Phil Thompson"
LICENSE = "GPL"
LIC_FILES_CHKSUM = "file://siplib/siplib.sbf.in;endline=15;md5=170cae4ecd1f7d4ba0934f7ed849306e"
DEPENDS = "python-native"
RDEPENDS_${PN} = "python-core"

#SRC_URI = "http://sourceforge.net/projects/pyqt/files/sip/sip-${PV}/sip-${PV}.tar.gz"

SRC_URI = "http://updateseng.vivint.com/innovation/downloads/sip-${PV}.tar.gz"

S = "${WORKDIR}/sip-${PV}"

TARGET_CC_ARCH += "${LDFLAGS}"

inherit distutils3-base

EXTRA_QMAKEVARS_POST += " TEMPLATE=lib \
                         CONFIG=console \
                         DESTDIR= \
                         VERSION=1.0.0 \
                         TARGET=sip \
                         DEFINES=SIP_QT_SUPPORT \
                         INCLUDEPATH+=. \
                         INCLUDEPATH+=${STAGING_INCDIR}/${PYTHON_DIR} \
                         INCLUDEPATH+=${STAGING_INCDIR}"

export BUILD_SYS
export HOST_SYS

do_configure(){
    ${PYTHON_DIR} configure.py
}

do_compile(){
	oe_runmake CC="${CC}" CXX="${CXX}" LINK="${CXX}"
}

do_install() {
	install -d ${D}${libdir}/${PYTHON_DIR}/site-packages/
	install -m 0755 ${S}/siplib/sip.so ${D}${libdir}/${PYTHON_DIR}/site-packages/sip.so
}

FILES_${PN} = "${libdir}/${PYTHON_DIR}/site-packages/sip.so"

SRC_URI[md5sum] = "8e48cd78bcd31f9310a701a4111c2739"
SRC_URI[sha256sum] = "d3141b65e48a30c9ce36612f8bcd1730ebf02d044757e4d6c5234927e2063e18"

