FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

LIC_FILES_CHKSUM = " \
    file://LICENSE.LGPL3;md5=e6a600fd5e1d9cbde2d983680233ad02 \
    file://LICENSE.GPL2;md5=b234ee4d69f5fce4486a80fdaf4a4263 \
    file://LICENSE.GPL3;md5=d32239bcb673463ab874e80d47fae504 \
    file://LICENSE.GPL3-EXCEPT;md5=763d8c535a234d9a3fb682c7ecb6c073 \
    file://LICENSE.FDL;md5=6d9f2a9af4c8b8c3c769f6cc1b6aaf7e \
    file://LICENSE.QT-LICENSE-AGREEMENT;md5=38de3b110ade3b6ee2f0b6a95ab16f1a \
"

SRC_URI = " \
     ${QT_GIT}/${QT_MODULE}.git;name=${QT_MODULE};${QT_MODULE_BRANCH_PARAM};protocol=${QT_GIT_PROTOCOL} \
"

SRC_URI += "\
    file://0001-Add-linux-oe-g-platform.patch \
    file://0002-cmake-Use-OE_QMAKE_PATH_EXTERNAL_HOST_BINS.patch \
    file://0003-qlibraryinfo-allow-to-set-qt.conf-from-the-outside-u.patch \
    file://0004-configure-bump-path-length-from-256-to-512-character.patch \
    file://0005-Disable-all-unknown-features-instead-of-erroring-out.patch \
    file://0006-Pretend-Qt5-wasn-t-found-if-OE_QMAKE_PATH_EXTERNAL_H.patch \
    file://0007-Delete-qlonglong-and-qulonglong.patch \
    file://0008-Replace-pthread_yield-with-sched_yield.patch \
    file://0009-Add-OE-specific-specs-for-clang-compiler.patch \
    file://0010-linux-clang-Invert-conditional-for-defining-QT_SOCKL.patch \
    file://0011-tst_qlocale-Enable-QT_USE_FENV-only-on-glibc.patch \
    file://0013-Disable-ltcg-for-host_build.patch \
    file://0014-Qt5GuiConfigExtras.cmake.in-cope-with-variable-path-.patch \
    file://0015-corelib-Include-sys-types.h-for-uint32_t.patch \
    file://0016-Define-QMAKE_CXX.COMPILER_MACROS-for-clang-on-linux.patch \
    file://0018-tst_qpainter-FE_-macros-are-not-defined-for-every-pl.patch \
    file://0019-Define-__NR_futex-if-it-does-not-exist.patch \
"

SRCREV = "29400a683f96867133b28299c0d0bd6bcf40df35"

