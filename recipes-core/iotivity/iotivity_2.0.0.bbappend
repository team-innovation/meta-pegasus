FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://compile_fixes.patch"

# Iotivity needs optimization for FORTIFY_SOURCES flag
CC_append = " -Os"
CXX_append = " -Os"
CPP_append = " -Os"
