export MACHINE=imx6dl-slimline
export EULA=1
export DISTRO=slimline
export BUILD_DIR=build
source ./fsl-setup-release.sh $BUILD_DIR
# reset BUILD_DIR to . because above cd'd to BUILD_DIR
export BUILD_DIR=.

grep -q meta-ruby $BUILD_DIR/conf/bblayers.conf ||
        echo "BBLAYERS += \" \${BSPDIR}/sources/meta-openembedded/meta-ruby \"" >> $BUILD_DIR/conf/bblayers.conf

grep -q meta-vivint $BUILD_DIR/conf/bblayers.conf ||
        echo "BBLAYERS += \" \${BSPDIR}/sources/meta-vivint \"" >> $BUILD_DIR/conf/bblayers.conf

grep -q meta-qt5 $BUILD_DIR/conf/bblayers.conf ||
        echo "BBLAYERS += \" \${BSPDIR}/sources/meta-qt5 \"" >> $BUILD_DIR/conf/bblayers.conf

ln -s ../sstate-cache . || true
grep -q package_rpm $BUILD_DIR/conf/local.conf &&
	sed -i -e s/package_rpm/package_ipk/ $BUILD_DIR/conf/local.conf
grep -q DIST_FEATURES_remove $BUILD_DIR/conf/local.conf ||
	echo "DISTRO_FEATURES_remove=\"x11 wayland directfb \"" >> $BUILD_DIR/conf/local.conf
grep -q HG_SERVER $BUILD_DIR/conf/local.conf ||
cat << '_END_' >> $BUILD_DIR/conf/local.conf
HG_SERVER = "scm.vivint.com/hg"
HG_APPS_TAG ?= "apps-hg"
HG_APPS_ID ?= "slimline"
_END_
