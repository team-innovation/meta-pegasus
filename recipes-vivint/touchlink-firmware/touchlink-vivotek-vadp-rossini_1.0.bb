DESCRIPTION = "Vivotek VADP module"
HOMEPAGE = "http://www.vivint.com"

#
# NOTE: change SRCREV and VADP_VERSION everytime there are
# changes in analytics source you want to be build
#

require touchlink-vivotek-vadp.inc

SRC_URI += "http://updateseng.vivint.com/innovation/downloads/Rossini_Toolchain.tar;name=toolchain64 \
            http://updateseng.vivint.com/innovation/downloads/vtcs_toolchain_32bit.tar.bz2;name=toolchain32 \
           " 

SRC_URI[toolchain32.md5sum] = "6a1f2ba7c8caf890e7e2fb412a804267"
SRC_URI[toolchain32.sha256sum] = "9d85f0ef0ccc0d3e78cdf51f1913e85e4415de183d2ebff444d8ff1f2107bd8d"

SRC_URI[toolchain64.md5sum] = "c15bef6a53b7515482abe204963eb3d7"
SRC_URI[toolchain64.sha256sum] = "0d18f5caaf562ec6705194f8517b867fdcbfe91d8568a7d46717b50d76444e7c"

VADP_MODULE_NAME = "rossini-vadp_${VADP_VERSION}.tar.gz"

do_compile_prepend() {
    sed -i "/TOOLCHAIN_PATH=/d" ./SOURCEME-ROSSINI
    if [ ${BUILD_ARCH} = "x86_64" ]; then
        TOOLCHAIN_PATH="${WORKDIR}/arm-eabi-uclibc/usr/bin" . ./SOURCEME-ROSSINI
    else
        TOOLCHAIN_PATH="${WORKDIR}/vtcs_toolchain/arm-eabi-uclibc/usr/bin" . ./SOURCEME-ROSSINI
    fi
}
