SUMMARY = "Object-Oriented HTTP framework"
HOMEPAGE = "https://github.com/cherrypy/cherrypy"
SECTION = "devel/python"

LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=a5ad8f932e1fd3841133f20d3ffedda1"

SRC_URI[sha256sum] = "729452f798ca756390046ef318043cae864a46814deaf3e6bd34dc959af198de"

DEPENDS = "${PYTHON_PN}-setuptools-scm-native"

RDEPENDS:${PN} += " \
    ${PYTHON_PN}-compression \
    ${PYTHON_PN}-crypt \
    ${PYTHON_PN}-datetime \
    ${PYTHON_PN}-email \
    ${PYTHON_PN}-fcntl \
    ${PYTHON_PN}-html \
    ${PYTHON_PN}-io \
    ${PYTHON_PN}-json \
    ${PYTHON_PN}-logging \
    ${PYTHON_PN}-netclient \
    ${PYTHON_PN}-netserver \
    ${PYTHON_PN}-profile \
    ${PYTHON_PN}-pydoc \
    ${PYTHON_PN}-xml \
    ${PYTHON_PN}-unixadmin \
    ${PYTHON_PN}-jaraco.collections \
    ${PYTHON_PN}-jaraco.text \
    ${PYTHON_PN}-jaraco.context \
    ${PYTHON_PN}-more-itertools \
"

RDEPENDS:${PN} += " \
    ${PYTHON_PN}-cheroot \
    ${PYTHON_PN}-contextlib2 \
    ${PYTHON_PN}-memcached \
    ${PYTHON_PN}-portend \
    ${PYTHON_PN}-pyopenssl \
    ${PYTHON_PN}-routes \
    ${PYTHON_PN}-simplejson \
    ${PYTHON_PN}-six \
    ${PYTHON_PN}-zc-lockfile \
"

PYPI_PACKAGE = "CherryPy"

inherit python_setuptools_build_meta pypi

BBCLASSEXTEND = "native nativesdk"
