export MACHINE=imx6dl-slimline
export EULA=1
source fsl-setup-release.sh -e fb -b build
grep -s meta-vivint $BUILD_DIR/conf/bblayers.conf ||
	echo "BBLAYERS += \" \${BSPDIR}/sources/meta-vivint \"" >> $BUILD_DIR/conf/bblayers.conf
ln -s ../sstat* .
