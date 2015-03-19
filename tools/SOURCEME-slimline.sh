export MACHINE=imx6dl-slimline
export EULA=1
export DISTRO=slimline
source fsl-setup-release.sh -e fb -b build
grep -q meta-vivint $BUILD_DIR/conf/bblayers.conf ||
	echo "BBLAYERS += \" \${BSPDIR}/sources/meta-vivint \"" >> $BUILD_DIR/conf/bblayers.conf
ln -s ../sstate-cache . || true
grep -q package_rpm $BUILD_DIR/conf/local.conf &&
	sed -i -e s/package_rpm/package_ipk/ $BUILD_DIR/conf/local.conf
