do_install_append() {
    if [ -d ${D}/usr/lib32 ]; then
        mv ${D}/usr/lib32 ${D}/usr/lib
    fi
}
