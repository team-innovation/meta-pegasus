DESCRIPTION = "Vivotek VADP module"
HOMEPAGE = "http://www.vivint.com"

#
# NOTE: change SRCREV and VADP_VERSION everytime there are
# changes in analytics source you want to be build
#

require touchlink-vivotek-vadp-dbc.inc

SRC_URI += " http://updateseng.vivint.com/innovation/downloads/DM36x_Toolchain.tar.gz;name=toolchain"

SRC_URI[toolchain.md5sum] = "9654e241499b808efeeafbfbf6449bfe"
SRC_URI[toolchain.sha256sum] = "1693ee0b3d543469bbb13157041a5c79e682aad765bf47b84a2be11576d35bd7"

VADP_MODULE_NAME = "dbc-vadp_${VADP_VERSION}.tar.gz"

do_compile_prepend() {
    sed -i "/TOOLCHAIN_PATH=/d" ./SOURCEME-DM36x
    TOOLCHAIN_PATH="${WORKDIR}/bin" . ./SOURCEME-DM36x
}

FILES_${PN} = "${FIRMWARE_DIR}/${VADP_MODULE_NAME}"
