FILESEXTRAPATHS_prepend := "${THISDIR}/qtbase:"

SRC_URI += "\
            file://qguiapplication-sync.patch \
           "

PACKAGECONFIG_append = " sql-sqlite"
