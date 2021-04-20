DESCRIPTION = "Install helper and uuu scripts to deploy directory"

LICENSE = "CLOSED"

FILES_${PN} = ""
ALLOW_EMPTY_${PN} = "1"

SRC_URI = "\
    file://uuu.auto \
    file://save-restore-serialnums.sh \
    file://uuu \
    file://README.md \
    "

do_deploy() {
    install -d ${DEPLOY_DIR_IMAGE}
    install -m 0755 ${WORKDIR}/uuu.auto ${DEPLOY_DIR_IMAGE}
    install -m 0755 ${WORKDIR}/save-restore-serialnums.sh ${DEPLOY_DIR_IMAGE}
    install -d ${DEPLOY_DIR_IMAGE}/uuu
    install -m 0755 ${WORKDIR}/uuu ${DEPLOY_DIR_IMAGE}/uuu
    install -m 0644 ${WORKDIR}/README.md ${DEPLOY_DIR_IMAGE}/uuu
}

addtask deploy after do_install
