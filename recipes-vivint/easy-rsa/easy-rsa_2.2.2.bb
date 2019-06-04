DESCRIPTION = "easy-rsa - Simple shell based CA utility"
HOMEPAGE = "https://github.com/OpenVPN/easy-rsa"
SECTION = "console/network"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${WORKDIR}/COPYING;md5=e944ef975ef9d0312e63c9ee80df17fc"
PRIORITY = "optional"

PR = "r21"

SRC_URI = "https://github.com/OpenVPN/easy-rsa/releases/download/${PV}/EasyRSA-${PV}.tgz \
		   file://vivint.patch \
		   file://create-all-certs \
		   file://COPYING \
		   file://client.ovpn"

S = "${WORKDIR}/EasyRSA-${PV}"

EASY_RSA_DIR = "${sysconfdir}/openvpn/easy-rsa"

do_install() {
	install -d ${D}/${EASY_RSA_DIR}
	cp -p ${S}/build-ca ${D}/${EASY_RSA_DIR}
	cp -p ${S}/build-dh ${D}/${EASY_RSA_DIR}
	cp -p ${S}/build-key ${D}/${EASY_RSA_DIR}
	cp -p ${S}/build-key-server ${D}/${EASY_RSA_DIR}
	cp -p ${S}/clean-all ${D}/${EASY_RSA_DIR}
	cp -p ${S}/openssl-1.0.0.cnf ${D}/${EASY_RSA_DIR}
	cp -p ${S}/pkitool ${D}/${EASY_RSA_DIR}
	cp -p ${S}/vars ${D}/${EASY_RSA_DIR}
	cp -p ${S}/whichopensslcnf ${D}/${EASY_RSA_DIR}
	cp -p ${WORKDIR}/create-all-certs  ${D}/${EASY_RSA_DIR}
	cp -p ${WORKDIR}/client.ovpn  ${D}/${EASY_RSA_DIR}
}

FILES_${PN} = "${EASY_RSA_DIR}/build-ca  \
			   ${EASY_RSA_DIR}/build-dh  \
               ${EASY_RSA_DIR}/build-key \
               ${EASY_RSA_DIR}/build-key-server  \
               ${EASY_RSA_DIR}/clean-all  \
               ${EASY_RSA_DIR}/client.ovpn  \
			   ${EASY_RSA_DIR}/create-all-certs \
			   ${EASY_RSA_DIR}/openssl-1.0.0.cnf \
		  	   ${EASY_RSA_DIR}/pkitool  \
			   ${EASY_RSA_DIR}/vars  \
			   ${EASY_RSA_DIR}/whichopensslcnf"

SRC_URI[md5sum] = "8b6002af8bfc217e0290a172d24e0c26"
SRC_URI[sha256sum] = "68da869085dde7c6a964e9c6104b86e4af91c4c592f8ec2a2f6cfc9d20808991"

pkg_postinst_ontarget_${PN} () {
#!/bin/sh
	 easy_rsa_dir="/media/extra/conf/openvpn_server/easy-rsa"

	 test -d $easy_rsa_dir || {
		mkdir -p $easy_rsa_dir
	 }
}

