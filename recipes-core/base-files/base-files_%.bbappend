FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

do_install_append() {
    install -d ${D}/media/extra
    install -d ${D}/media/mmcblk2p2
    install -d ${D}/media/mmcblk2p3
}

FILES_${PN} += " /media"

# EMB-14941
# clean up Gallus and Nene
pkg_postinst_ontarget_${PN} () {
	if grep -q vivint,nene "/proc/device-tree/compatible" || grep -q vivint,gallus "/proc/device-tree/compatible" ; then
		rm -rf /media/mmcblk2p5 /media/mmcblk2p6
	fi
}
