do_install:append() {
        echo "LSB_VERSION=${PEGASUS_VERSION}" >> ${D}${sysconfdir}/lsb-release
}
