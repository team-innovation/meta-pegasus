FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
SRC_URI_append += " \
    file://qt-mouse-area.patch \
"

