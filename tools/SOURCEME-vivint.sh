export BUILD_DIR=$1
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
