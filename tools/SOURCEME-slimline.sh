export BUILD_DIR=$1
# use "build" as the default build dir
: ${BUILD_DIR:=build}

_APPS_TAG="embedded-apps"
_APPS_REV='${AUTOREV}'
_APPS_BRANCH="release/3.10.4"
_OPENWRT_BRANCH="check"

# if already set up then we just run the setup-environment script
test -d ${BUILD_DIR} &&
	sed -i '/GIT_SERVER/d' ${BUILD_DIR}/conf/local.conf && 
	sed -i '/GIT_APPS_TAG/d' ${BUILD_DIR}/conf/local.conf &&
	sed -i '/GIT_APPS_REV/d' ${BUILD_DIR}/conf/local.conf && 
    sed -i '/GIT_APPS_BRANCH/d' ${BUILD_DIR}/conf/local.conf && 
	sed -i '/OPENWRT_BRANCH/d' ${BUILD_DIR}/conf/local.conf &&
	sed -i '/UPDATESENG/d' ${BUILD_DIR}/conf/local.conf && 
    sed -i '/PRSERV_HOST/d'  ${BUILD_DIR}/conf/local.conf && 
	echo "GIT_SERVER ?= \"git@source.vivint.com:7999/em\"" >> ${BUILD_DIR}/conf/local.conf &&
	echo "GIT_APPS_TAG ?= \"${_APPS_TAG}\"" >> ${BUILD_DIR}/conf/local.conf &&
	echo "GIT_APPS_REV ?= \"${_APPS_REV}\"" >> ${BUILD_DIR}/conf/local.conf &&
	echo "GIT_APPS_BRANCH ?= \"${_APPS_BRANCH}\"" >> ${BUILD_DIR}/conf/local.conf &&
	echo "OPENWRT_BRANCH ?= \"${_OPENWRT_BRANCH}\"" >> ${BUILD_DIR}/conf/local.conf &&
	echo "UPDATESENG ?= \"updateseng.vivint.com/innovation\"" >> ${BUILD_DIR}/conf/local.conf &&
    echo "PRSERV_HOST = \"localhost:0\"" >> ${BUILD_DIR}/conf/local.conf &&
	grep -q meta-vivint ${BUILD_DIR}/conf/bblayers.conf &&
	grep -q oe-meta-go ${BUILD_DIR}/conf/bblayers.conf &&
	source setup-environment ${BUILD_DIR} &&
	return

# it is a new dir
echo Setting up new dir \"${BUILD_DIR}\"
export MACHINE=imx6dl-slimline
export EULA=1
export DISTRO=slimline

source ./fsl-setup-release.sh -b ${BUILD_DIR}

grep -q meta-vivint ./conf/bblayers.conf ||
	echo "BBLAYERS += \" \${BSPDIR}/sources/meta-vivint \"" \
		>> ./conf/bblayers.conf

grep -q oe-meta-go ./conf/bblayers.conf ||
	echo "BBLAYERS += \" \${BSPDIR}/sources/oe-meta-go \"" \
		>> ./conf/bblayers.conf

ln -s ../sstate-cache . || true

grep -q package_rpm ./conf/local.conf &&
	sed -i -e s/package_rpm/package_ipk/ ./conf/local.conf

grep -q DIST_FEATURES_remove ./conf/local.conf ||
	echo "DISTRO_FEATURES_remove=\"x11 wayland directfb \"" \
		>> ./conf/local.conf

grep -q GIT_SERVER ./conf/local.conf || 
	echo -e "GIT_SERVER ?= \"git@source.vivint.com:7999/em\"\nGIT_APPS_TAG ?= \"${_APPS_TAG}\"\nGIT_APPS_REV ?=\"${_APPS_REV}\"\nGIT_APPS_BRANCH ?= \"${_APPS_BRANCH}\"\nOPENWRT_BRANCH ?= \"${_OPENWRT_BRANCH}\"\nUPDATESENG = \"updateseng.vivint.com/innovation\"\nPRSERV_HOST = \"localhost:0\"" >> ./conf/local.conf
