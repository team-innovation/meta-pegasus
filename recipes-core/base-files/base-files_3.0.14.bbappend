FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

dirs755_append = " /media/ram /media/card /media/extra /media/bootscript /media/mmcblk0p6 /media/mmcblk0p5 "

PR = "r91"

BB_HASH_IGNORE_MISMATCH = "1"

SRC_URI += "file://vivintbuild_gpg.pub  \
	    file://ca_gpg_1.pub  \
	    file://ca_gpg_2.pub  \
            "

do_install_append () {
	rm ${D}${sysconfdir}/issue*
	install -d ${D}${datadir}/keys
	install -m 0644 ${WORKDIR}/vivintbuild_gpg.pub  ${D}${datadir}/keys/vivintbuild_gpg.pub 
	install -m 0644 ${WORKDIR}/ca_gpg_1.pub ${D}${datadir}/keys/ca_gpg_1.pub
	install -m 0644 ${WORKDIR}/ca_gpg_2.pub ${D}${datadir}/keys/ca_gpg_2.pub
}

pkg_postinst_${PN} () {
#!/bin/sh -e
if [ x"$D" = "x" ]; then
	if [ ! -e /media/extra/conf/vivintbuild_gpg.pub ]; then
		echo "Copy public key to /media/extra/conf"
		cp -a /usr/share/keys/vivintbuild_gpg.pub /media/extra/conf/vivintbuild_gpg.pub
	fi	

	if [ ! -e /media/extra/conf/ca_gpg_1.pub ]; then
		echo "Copy public key to /media/extra/conf"
		cp -a /usr/share/keys/ca_gpg_1.pub /media/extra/conf/ca_gpg_1.pub
	fi	

	if [ ! -e /media/extra/conf/ca_gpg_2.pub ]; then
		echo "Copy public key to /media/extra/conf"
		cp -a /usr/share/keys/ca_gpg_2.pub /media/extra/conf/ca_gpg_2.pub
	fi	
fi

}
