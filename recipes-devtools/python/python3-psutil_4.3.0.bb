DESCRIPTION = "psutil for Python"
SECTION = "devel/python"
PRIORITY = "optional"
LICENSE = "PSF"
LIC_FILES_CHKSUM = "file://LICENSE;md5=0f02e99f7f3c9a7fe8ecfc5d44c2be62"
SRCNAME = "psutil"
PR = "ml"

DEPENDS += "python3"
DEPENDS_virtclass-native += "python3-native"

#SRC_URI = "git://github.com/thelinuxdude/python-pulseaudio.git;protocol=git;branch=pa-4.0"

SRC_URI = " \
	git://github.com/giampaolo/psutil.git;protocol=git;tag=release-${PV}"
S = "${WORKDIR}/git"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native"

NATIVE_INSTALL_WORKS = "1"

inherit setuptools3

do_configure_prepend() {
	if ${@['true', 'false'][bb.data.inherits_class('native', d)]}
    	then
		sed -i 's|processor|Processor|g' ${S}/psutil/_pslinux.py
        fi
}

do_install_prepend() {
    install -d ${D}/${libdir}/${PYTHON_DIR}/site-packages
}

SRC_URI[md5sum] = "0899da2bdfc6821a62877eba8b76deb9"
SRC_URI[sha256sum] = "2de897263fa4f00e4e8316dff53f8e3631780f7567b2035129b7400d477858d6"
SRC_URI[sha1sum] = "e4276dd11b5f03c4397490736f6c6445a7e64798"

INSANE_SKIP_${PN} = "True"
