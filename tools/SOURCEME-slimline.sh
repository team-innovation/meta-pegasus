export BUILD_DIR=$1
# use "build" as the default build dir
: ${BUILD_DIR:=build}

_APPS_TAG="apps-hg"
_APPS_ID="camera"

# if already set up then we just run the setup-environment script
test -d ${BUILD_DIR} &&
	sed -i '/HG_APPS_TAG/d' ${BUILD_DIR}/conf/local.conf &&
	sed -i '/HG_APPS_ID/d' ${BUILD_DIR}/conf/local.conf && 
	sed -i '/UPDATESENG/d' ${BUILD_DIR}/conf/local.conf && 
	echo "HG_APPS_TAG ?= \"${_APPS_TAG}\"" >> ${BUILD_DIR}/conf/local.conf &&
	echo "HG_APPS_ID ?= \"${_APPS_ID}\"" >> ${BUILD_DIR}/conf/local.conf &&
	echo "UPDATESENG ?= \"updateseng.vivint.com/innovation\"" >> ${BUILD_DIR}/conf/local.conf &&
	grep -q meta-vivint ${BUILD_DIR}/conf/bblayers.conf &&
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

ln -s ../sstate-cache . || true

grep -q package_rpm ./conf/local.conf &&
	sed -i -e s/package_rpm/package_ipk/ ./conf/local.conf

grep -q DIST_FEATURES_remove ./conf/local.conf ||
	echo "DISTRO_FEATURES_remove=\"x11 wayland directfb \"" \
		>> ./conf/local.conf

grep -q HG_SERVER ./conf/local.conf || 
	echo -e "HG_SERVER = \"scm.vivint.com/hg\"\nHG_APPS_TAG ?= \"${_APPS_TAG}\"\nHG_APPS_ID ?= \"${_APPS_ID}\"\nUPDATESENG = \"updateseng.vivint.com/innovation\"" >> ./conf/local.conf
