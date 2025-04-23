do_compile_append() {
    if [ -e ${S}/scripts/audio_file_check.py ]; then
        python3 ${S}/scripts/audio_file_check.py
        if [ $? -ne 0 ]; then
            exit 1
        fi
    fi
}

do_install_append() {
	install -d ${D}/${INSTALL_DIR}/soundfiles/midi
    ${RSYNC_CMD} ${S}/soundfiles/midi/ ${D}/${INSTALL_DIR}/soundfiles/midi
}

FILES_${PN}-sound-midi = "\
    ${INSTALL_DIR}/soundfiles/midi/* \
"
