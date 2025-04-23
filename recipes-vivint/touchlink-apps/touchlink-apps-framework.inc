RDEPENDS_${PN}-framework = "\
    python3-dateutil \
    python3-setproctitle \
    python3-urllib3 \
    python3-multiprocessing \
    python3-threading \
    python3-jsonschema \
    python3-jacobs-json-schema \
    python3-six \
    python3-jmespath \
    python3-msgpack \
    python3-requests \
    python3-requests-toolbelt \       
"

do_install_append () {
    install -d ${D}/${FRAMEWORK_DIR}/taurine/
	${RSYNC_CMD} ${S}/code/framework/taurine/ ${D}/${FRAMEWORK_DIR}/taurine/

	find ${D}/${FRAMEWORK_DIR}/taurine -name proxies -prune -o -name *.py -print | xargs rm -f
}

FILES_${PN}-framework = "\
	${FRAMEWORK_DIR}/* \
"	
