DESCRIPTION = "Python Qt5 Bindings"
HOMEPAGE = "http://riverbankcomputing.co.uk"
AUTHOR = "Phil Thomson @ riverbank.co.uk"
SECTION = "devel/python"
LICENSE = "GPLv2 & GPLv3 | GPL_EXCEPTION"
LIC_FILES_CHKSUM = "\
    file://LICENSE;md5=027a10affabd63483e5a6ef03ed8590a \
"

DEPENDS = "python3-sip-native python3-sip python3-native qtbase qtmultimedia qtwebkit qtquick1 qtdeclarative qtxmlpatterns"
RDEPENDS_${PN} = "python3-core"

inherit python-dir qmake5_base distutils3-base
inherit native

PYQT_OE_VERSION = "Qt_5_4_1"
PR = "r1"

CONF_FILE = "touchlink_pyqt5_native.cfg"

#SRC_URI = "http://sourceforge.net/projects/pyqt/files/PyQt5/PyQt-${PV}/PyQt-gpl-${PV}.tar.gz 

SRC_URI = "http://updateseng.vivint.com/innovation/downloads/PyQt-gpl-5.4.tar.gz \
	   file://pyqt-commercial.sip \
	   file://${CONF_FILE} \
	  "

SRC_URI[md5sum] = "7f2eb79eaf3d7e5e7df5a4e9c8c9340e"
SRC_URI[sha256sum] = "760264f5faa68ae22900d27ac114f3a7fffbb9b3ec12e7a62a0a45c887517731"

S = "${WORKDIR}/PyQt-gpl-5.4"

CONFIG_DIR = "${S}/configurations"

export QT_INSTALL_HEADERS="${STAGING_INCDIR}/qt5"

export OE_QMAKE_CC = "${CC}"
export OE_QMAKE_CXX = "${CXX}"
export OE_QMAKE_CXXFLAGS = "${CXXFLAGS} -I${STAGING_INCDIR}/${PYTHON_DIR} "
export OE_QMAKE_LINK = "${CXX}"
export OE_QMAKE_CFLAGS = "${CFLAGS} -I${STAGING_INCDIR}/${PYTHON_DIR} "
export OE_QMAKE_LDFLAGS = "${LDFLAGS}"
export OE_QMAKE_AR = "${AR}"
export INSTALL_ROOT = "${STAGING_DIR}/${MULTIMACH_TARGET_SYS}"

#SIP_MODULES = "QtCore QtGui QtMultimedia QtMultimediaWidgets QtNetwork QtOpenGL QtQml QtQuick QtWebKit QtWebKitWidgets QtWidgets QtXmlPatterns _QOpenGLFunctions_ES2"
SIP_MODULES = "QtCore QtNetwork"

do_configure_prepend() {
	mkdir -p ${CONFIG_DIR}
	cp ${WORKDIR}/pyqt-commercial.sip ${S}/sip
	cp ${WORKDIR}/${CONF_FILE} ${CONFIG_DIR}
}

do_configure() {
	${PYTHON_DIR} ${S}/configure.py \
		--qmake ${STAGING_BINDIR_NATIVE}/qt5/qmake \
		--configuration=${CONFIG_DIR}/${CONF_FILE} \
		--confirm-license --verbose
}

do_install() {
    install -d ${D}${libdir}/${PYTHON_DIR}/site-packages/PyQt5
	install -m 0644 ${S}/__init__.py ${D}${libdir}/${PYTHON_DIR}/site-packages/PyQt5
    for module in ${SIP_MODULES}
    do
        echo "from PyQt5.${module} import *" >> ${D}${libdir}/${PYTHON_DIR}/site-packages/PyQt5/Qt.py
    done

    for module in ${SIP_MODULES}
    do
        install -m 0755 ${WORKDIR}/build/${module}/lib${module}.so ${D}${libdir}/${PYTHON_DIR}/site-packages/PyQt5/${module}.so
    done
}

do_install_append() {
    install -d ${D}${datadir}/sip/qt
    install -d ${STAGING_LIBDIR}/${PYTHON_DIR}/site-packages
    for module in ${SIP_MODULES}
    do
        install -m 0644 ${S}/sip/${module}/*.sip ${D}${datadir}/sip/qt
    	install -m 0755 ${WORKDIR}/build/${module}/lib${module}.so ${STAGING_LIBDIR}/${PYTHON_DIR}/site-packages/${module}.so
    done
}


FILES_${PN}-dbg = "${libdir}/${PYTHON_DIR}/site-packages/PyQt5/.debug"
FILES_${PN} = "${libdir}/${PYTHON_DIR}/site-packages"
