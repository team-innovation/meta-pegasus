FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
BBCLASSEXTEND = "native nativesdk"

SRC_URI += " file://version_fix.patch"
