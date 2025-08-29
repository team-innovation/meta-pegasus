do_install:append() {
    if [ "${MLPREFIX}" == "lib32-" ]; then
        install -d ${D}${bindir}/32
        mv ${D}${bindir}/python${PYTHON_MAJMIN} ${D}${bindir}/32/python${PYTHON_MAJMIN}
        mv ${D}${bindir}/python${PYTHON_MAJMIN}m ${D}${bindir}/32/python${PYTHON_MAJMIN}m
        mv ${D}${bindir}/python3 ${D}${bindir}/32/python3
    fi
}
