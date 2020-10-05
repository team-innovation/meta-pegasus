SUMMARY = "Z/IP Gateway"
HOMEPAGE = "http://zts.sigmadesigns.com"
SECTION = "network"
LICENSE = "CLOSED"
LIC_FILES_CHKSUM = "file://LICENSE;md5=c5572362acb437d9c5e365a4198a459b"

DEPENDS = "python-native libusb flex json-c openssl11"
RDEPENDS_${PN} = "bridge-utils openssl11"

PR = "r1"
PV = "7.14.01+git${SRCPV}"

SRCREV = "a4fe258bde8c871d57e2929f24fa5a7434e7016c"
SRCBRANCH = "v7.14.1"

GIT_ZGATE_SERVER ?= "${GIT_SERVER}"
GIT_ZGATE_PROTOCOL ?= "ssh"

SRC_URI = "git://${GIT_ZGATE_SERVER}/zware_controller_sdk;protocol=${GIT_ZGATE_PROTOCOL};branch=${SRCBRANCH} \
           file://zwaved \
           "

S = "${WORKDIR}/git/zipgateway-7.14.01-Source/usr/local"

inherit pkgconfig cmake python-dir pythonnative update-rc.d

INITSCRIPT_NAME = "zwaved"
INITSCRIPT_PARAMS = "start 30 5 ."

EXTRA_OECMAKE = " \
    -DCMAKE_INSTALL_PREFIX=/usr/local \
    -DSKIP_TESTING=TRUE \
    -DDISABLE_MOCK=TRUE \
    -DJSON_C_SRC=/usr \
    -DDISABLE_DTLS=TRUE \
"
cmake_do_generate_toolchain_file_append() {
	cat >> ${WORKDIR}/toolchain.cmake << EOF
set ( CMAKE_LIBRARY_PATH ${libdir}/openssl11/lib ${libdir} ${base_libdir})
set ( ENV{PKG_CONFIG_PATH} ${STAGING_LIBDIR}/openssl11/lib/pkgconfig:$ENV{PKG_CONFIG_PATH})
EOF
}

do_configure_prepend() {
	sed -i -e 's/PkgConfig::JSON_C/\x24{JSON_C_LDFLAGS}/g' ${S}/systools/zw_nvm_converter/controllerlib_7.xx/CMakeLists.txt
	sed -i -e 's/PkgConfig::JSON_C/\x24{JSON_C_LDFLAGS}/g' ${S}/systools/zgw_restore/CMakeLists.txt
	sed -i -e 's/-Werror/\x24{JSON_C_CFLAGS} -Werror/g' ${S}/systools/zgw_restore/CMakeLists.txt

	sed -i -e 's/OpenSSL::SSL OpenSSL::Crypto/\x24{OPENSSL_LIBRARIES}/g' ${S}/src/CMakeLists.txt
	sed -i -e 's/target_compile_options( zipgateway-lib PUBLIC )/target_compile_options( zipgateway-lib PUBLIC \x24{_OPENSSL_CFLAGS})/g' ${S}/src/CMakeLists.txt
}

do_install_append() {
    install -d ${D}${sysconfdir}/init.d
    install -m 0755 ${WORKDIR}/zwaved ${D}${sysconfdir}/init.d

    rm -f ${D}/${prefix}/local/etc/zipgateway_node_identify_rpi3b+_led.sh
}

FILES_${PN} += "${prefix}/local/sbin/zipgateway"
FILES_${PN} += "${prefix}/local/sbin/udprelay"
FILES_${PN} += "${prefix}/local/etc/zipgateway.cfg"
FILES_${PN} += "${prefix}/local/etc/zipgateway_provisioning_list.cfg"
FILES_${PN} += "${prefix}/local/etc/zipgateway_node_identify_generic.sh"
FILES_${PN} += "${prefix}/local/etc/zipgateway.tun"
FILES_${PN} += "${prefix}/local/etc/zipgateway.fin"
FILES_${PN} += "${prefix}/local/etc/ZIPR.x509_1024.pem"
FILES_${PN} += "${prefix}/local/etc/ZIPR.key_1024.pem"
FILES_${PN} += "${prefix}/local/etc/Portal.ca_x509.pem"
FILES_${PN} += "${sysconfdir}/init.d/zwaved"
FILES_${PN} += "${sysconfdir}/rcS.d/*zwaved"
FILES_${PN} += "${prefix}/local/man/man3/zipgateway.3"
FILES_${PN} += "${prefix}/local/bin/zgw_convert_eeprom"
FILES_${PN} += "${prefix}/local/bin/zgw_import.sh"
FILES_${PN} += "${prefix}/local/bin/zgw_backup.sh"
FILES_${PN} += "${prefix}/local/bin/zgw_recover.sh"
FILES_${PN} += "${prefix}/local/bin/zgw_restore"
FILES_${PN} += "${prefix}/local/bin/zw_nvm_converter"
FILES_${PN} += "${prefix}/local/bin/zw_programmer"

FILES_${PN}-dbg += "${prefix}/local/bin/.debug"
FILES_${PN}-dbg += "${prefix}/local/sbin/.debug"

RPROVIDES_${PN} = "zipgateway"

