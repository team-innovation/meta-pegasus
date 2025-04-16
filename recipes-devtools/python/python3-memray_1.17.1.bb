SUMMARY = "A memory profiler for Python applications"
HOMEPAGE = "https://github.com/bloomberg/memray"
SECTION = "devel/python"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=5de5671aa80e3d8ebba56399d18cad14"

SRC_URI[sha256sum] = "99f6672d435878e3251a9c4600bb8f14cf205d2d6da3d6f0e6b309e535f9fc4a"

DEPENDS += "\
    python3-cython-native \
    python3-pkgconfig-native \
    pkgconfig-native \
    lz4 \
    libunwind \
    elfutils \
"

inherit python_setuptools_build_meta pypi

do_compile:prepend() {
    export HOST_SYS="${HOST_SYS}"
    export BUILD_SYS="${BUILD_SYS}"
    export CC="${CC}"
    export CXX="${CXX}"
    export CPP="${CPP}"
    export LD="${LD}"

    # Patch libbacktrace's configure call with cross args
    sed -i \
        's|./configure|./configure --host=${HOST_SYS} --build=${BUILD_SYS}|' \
        ${S}/src/memray/allocator/build_libbacktrace.py
}