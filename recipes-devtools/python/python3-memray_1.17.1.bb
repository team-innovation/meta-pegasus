SUMMARY = "A memory profiler for Python applications"
HOMEPAGE = "https://github.com/bloomberg/memray"
SECTION = "devel/python"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=5de5671aa80e3d8ebba56399d18cad14"

SRC_URI[sha256sum] = "99f6672d435878e3251a9c4600bb8f14cf205d2d6da3d6f0e6b309e535f9fc4a"

DEPENDS = "\
    ${PYTHON_PN}-setuptools-scm-native \
    ${PYTHON_PN}-pkgconfig-native \
    ${PYTHON_PN}-cython-native \
    lz4 \
    libunwind \
    autoconf-native \
    automake-native \
    libtool-native \
"

inherit setuptools3 pypi

do_compile() {
    export CC="${CC}"
    export CXX="${CXX}"
    export LD="${LD}"
    export AR="${AR}"
    export RANLIB="${RANLIB}"
    export READELF="${READELF}"

    cd ${S}/src/vendor/libbacktrace
    ./configure \
        --with-pic \
        --host=${HOST_SYS} \
        --prefix=${S}/src/vendor/libbacktrace/install \
        --includedir=${S}/src/vendor/libbacktrace/install/include/libbacktrace

    oe_runmake

    cd ${S}
    python3 setup.py build
}