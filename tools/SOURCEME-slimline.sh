export MACHINE=imx6dl-slimline
export EULA=1
export DISTRO=slimline
source fsl-setup-release.sh -e fb -b build
grep -q meta-vivint $BUILD_DIR/conf/bblayers.conf ||
	echo "BBLAYERS += \" \${BSPDIR}/sources/meta-vivint \"" >> $BUILD_DIR/conf/bblayers.conf
ln -s ../sstate-cache . || true
grep -q package_rpm $BUILD_DIR/conf/local.conf &&
	sed -i -e s/package_rpm/package_ipk/ $BUILD_DIR/conf/local.conf
grep -q HG_SERVER $BUILD_DIR/conf/local.conf ||
cat << '_END_' >> $BUILD_DIR/conf/local.conf
HG_SERVER = "scm.vivint.com/hg"
HG_APPS_TAG ?= "apps-hg"
HG_APPS_ID ?= "slimline"
_END_
