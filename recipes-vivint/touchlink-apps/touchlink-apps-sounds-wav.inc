do_compile_append() {
    if [ -e ${S}/scripts/audio_file_check.py ]; then
        python3 ${S}/scripts/audio_file_check.py
        if [ $? -ne 0 ]; then
            exit 1
        fi
    fi
}

do_install_append() {
	install -d ${D}/${INSTALL_DIR}/soundfiles/wav
    ${RSYNC_CMD} ${S}/soundfiles/wav/ ${D}/${INSTALL_DIR}/soundfiles/wav
}

FILES_${PN}-sound-wav = "\
    ${INSTALL_DIR}/soundfiles/wav/* \
"
