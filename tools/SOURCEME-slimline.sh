export BUILD_DIR=$1
# use "build" as the default build dir
: ${BUILD_DIR:=build}

# if already set up then we just run the setup-environment script
test -d ${BUILD_DIR} &&
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
	cat <<-'_END_' >> ./conf/local.conf
		HG_SERVER = "scm.vivint.com/hg"
		HG_APPS_TAG ?= "apps-hg"
		HG_APPS_ID ?= "slimline"
	_END_
