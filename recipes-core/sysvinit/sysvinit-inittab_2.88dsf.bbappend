do_install_append() {
    echo "" >> ${D}${sysconfdir}/inittab
    echo "GS0:12345:respawn:/sbin/getty -L 115200 ttyGS0 vt102" >> ${D}${sysconfdir}/inittab
}
