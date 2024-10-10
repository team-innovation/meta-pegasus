export BUILD_DIR=$1

BB_ENV_PASSTHROUGH_ADDITIONS=" \
    $BB_ENV_PASSTHROUGH_ADDITIONS                          
    GIT_UBOOT_SERVER \
    GIT_UBOOT_BRANCH \
    GIT_UBOOT_PROTOCOL \
    GIT_UBOOT_REV \
    GIT_UBOOT_REPO \
    GIT_UBOOT_NGH_SERVER \
    GIT_UBOOT_NGH_BRANCH \
    GIT_UBOOT_NGH_PROTOCOL \
    GIT_UBOOT_NGH_REV \
    GIT_UBOOT_NGH_REPO \
    GIT_KERNEL_SERVER \
    GIT_KERNEL_BRANCH \
    GIT_KERNEL_NGH_BRANCH \
    GIT_KERNEL_PROTOCOL \
    GIT_KERNEL_REV \
    GIT_KERNEL_REPO \
    GIT_KERNEL_NGH_REV \
    GIT_AUDIO_SERVER \
    GIT_AUDIO_BRANCH \
    GIT_AUDIO_PROTOCOL \
    GIT_AUDIO_REV \
    GIT_AUDIO_REPO \
    GIT_APPS_SERVER \
    GIT_APPS_BRANCH \
    GIT_APPS_PROTOCOL \
    GIT_APPS_REV \
    GIT_APPS_REPO \
    GIT_ARTIFACTS_BRANCH \
    GIT_ARTIFACTS_SERVER \
    GIT_ARTIFACTS_PROTOCOL \
    GIT_ARTIFACTS_REV \
    GIT_ARTIFACTS_REPO \
    GIT_ARTIFACTS_LATEST_BRANCH \
    GIT_ARTIFACTS_LATEST_SERVER \
    GIT_ARTIFACTS_LATEST_PROTOCOL \
    GIT_ARTIFACTS_LATEST_REV \
    GIT_ARTIFACTS_LATEST_REPO \
    GIT_DECT_CMD_SERVER \
    GIT_DECT_CMD_BRANCH \
    GIT_DECT_CMD_PROTOCOL \
    GIT_DECT_CMD_REV \
    GIT_DECT_CMD_REPO \
    GIT_SIMLOCK_SERVER \
    GIT_SIMLOCK_PROTOCOL \
    GIT_SIMLOCK_BRANCH \
    GIT_SIMLOCK_REV \
    GIT_SIMLOCK_REPO \
    GIT_META_VIVINT_BRANCH \
    GIT_META_VIVINT_REV \
    GIT_META_VIVINT_NGH_BRANCH \
    GIT_META_VIVINT_NGH_REV \
    GIT_META_VIVINT_NGH_REPO \
    GIT_LIVE555_BRANCH \
    GIT_LIVE555_SERVER \
    GIT_LIVE555_PROTOCOL \
    GIT_LIVE555_REV \
    GIT_LIVE555_REPO \
    GIT_LIVE555PROXY_BRANCH \
    GIT_LIVE555PROXY_SERVER \
    GIT_LIVE555PROXY_PROTOCOL \
    GIT_LIVE555PROXY_REV \
    GIT_LIVE555PROXY_REPO \
    GIT_DSPG_BRANCH \
    GIT_DSPG_SERVER \
    GIT_DSPG_PROTOCOL \
    GIT_DSPG_REV \
    GIT_DSPG_REPO \
    GIT_SERVER \
    LOCK_PORTS \
    REDUCE_LOGS \
    GIT_STRINGS_SERVER \
    SLIMLINE_VERSION \
    BY_PASS_UNIT_TEST \
    UPDATE_STRING_TABLE \
    BUILD_TYPE \
    CAMERA_PROTOBUF_BRANCH \
    CAMERA_PROTOBUF_SERVER \
    CAMERA_PROTOBUF_PROTOCOL \
    CAMERA_PROTOBUF_REV \
    CAMERA_PROTOBUF_REPO \
    VIVINT_AIR_UTILS_BRANCH \
    VIVINT_AIR_UTILS_SERVER \
    VIVINT_AIR_UTILS_REV \
    TLS_PATH \
    WORK_PATH \
    GIT_HWD_SERVER \
    GIT_HWD_BRANCH \
    GIT_HWD_PROTOCOL \
    GIT_HWD_REV \
    GIT_HWD_REPO \
    GIT_ZGATE_SERVER \
    GIT_ZGATE_BRANCH_NGH \
    GIT_ZGATE_PROTOCOL \
    GIT_ZGATE_REV \
    GIT_ZGATE_REPO \
    GIT_PLUGIN_SERVER \
    GIT_PLUGIN_BRANCH \
    GIT_PLUGIN_PROTOCOL \
    GIT_PLUGIN_REV \
    GIT_PLUGIN_REPO \
"

export BB_ENV_PASSTHROUGH_ADDITIONS

# use "build" as the default build dir
: "${BUILD_DIR:=build}"

# if already set up then we just run the setup-environment script
test -d "${BUILD_DIR}" &&
	source setup-environment "${BUILD_DIR}" &&
	return

# If we fail the above, just re-create the conf/ files
rm -rf "${BUILD_DIR}"/conf/

# it is a new dir
echo Setting up new dir \""${BUILD_DIR}"\"
export MACHINE=imx8mm-magellan
export EULA=1
export DISTRO=vivint-wayland

source ./imx-setup-release.sh -b "${BUILD_DIR}"

grep -q meta-vivint ./conf/bblayers.conf ||
	echo "BBLAYERS += \" \${BSPDIR}/sources/meta-vivint \"" \
		>> ./conf/bblayers.conf

grep -q meta-vivint ./conf/bblayers.conf ||
	echo "BBLAYERS += \" \${BSPDIR}/sources/meta-vivint-apps \"" \
		>> ./conf/bblayers.conf

grep -q meta-wnc ./conf/bblayers.conf ||
    echo "BBLAYERS += \" \${BSPDIR}/sources/meta-wnc \"" \
            >> ./conf/bblayers.conf

grep -q package_ ./conf/local.conf &&
	sed -i -e s/package_rpm/package_ipk/ ./conf/local.conf
	sed -i -e s/package_deb/package_ipk/ ./conf/local.conf
