SUMMARY = "Object-Oriented HTTP framework"
HOMEPAGE = "https://github.com/cherrypy/cherrypy"
SECTION = "devel/python"

LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=a5ad8f932e1fd3841133f20d3ffedda1"

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

# pypi class generates old URL format that PyPI no longer supports for 18.10.0
SRC_URI = "https://files.pythonhosted.org/packages/93/e8/2f7ef142d1962d08a8885c4c9942212abecad6a80ccdd1620fd1f5c993fd/cherrypy-18.10.0.tar.gz;downloadfilename=cherrypy-18.10.0.tar.gz"
SRC_URI[sha256sum] = "6c70e78ee11300e8b21c0767c542ae6b102a49cac5cfd4e3e313d7bb907c5891"

# Tarball unpacks to cherrypy-18.10.0/, not python3-cherrypy-18.10.0/
S = "${WORKDIR}/cherrypy-${PV}"

inherit python_setuptools_build_meta

BBCLASSEXTEND = "native nativesdk"