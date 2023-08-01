FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"


SRC_URI = " \
    ${QT_GIT}/${QT_MODULE}.git;name=${QT_MODULE};${QT_MODULE_BRANCH_PARAM};protocol=${QT_GIT_PROTOCOL} \
    file://0001-Use-OE_QMAKE_PATH_EXTERNAL_HOST_BINS-to-locate-qmlca.patch \
    file://qt-mouse-area.patch \
"

SRCREV = "540c4e4a5def8c350a49bb68380b787ae62490c6"

