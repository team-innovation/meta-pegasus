python() {
    d.appendVar('RPROVIDES_class-native', ' python3-misc-native')
}

do_install_append() {
    if [ "${MLPREFIX}" == "lib32-" ]; then
        install -d ${D}${bindir}/32
        cp ${D}${bindir}/python${PYTHON_MAJMIN} ${D}${bindir}/32/python${PYTHON_MAJMIN}
        cp ${D}${bindir}/python${PYTHON_MAJMIN}m ${D}${bindir}/32/python${PYTHON_MAJMIN}m
        cp ${D}${bindir}/python3 ${D}${bindir}/32/python3
    fi
}
