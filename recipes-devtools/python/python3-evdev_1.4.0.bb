SUMMARY = "Generic input event"
DESCRIPTION = "Provides bindings to the generic input event interface in Linux"
HOMEPAGE = "https://pypi.org/project/evdev"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=18debddbb3f52c661a129724a883a8e2"

SRC_URI[md5sum] = "919c1107b576771cfb0c43e2a8a4a405"
SRC_URI[sha256sum] = "8782740eb1a86b187334c07feb5127d3faa0b236e113206dfe3ae8f77fb1aaf1"

PYPI_PACKAGE = "evdev"
inherit setuptools3 pypi

RDEPENDS_${PN} = "\
    ${PYTHON_PN}-asyncio \
"

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native nativesdk"
