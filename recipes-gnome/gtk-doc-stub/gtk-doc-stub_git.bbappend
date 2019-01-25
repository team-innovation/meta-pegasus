FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

PROVIDES = "gtk-doc"

SRC_URI_append = " file://0001-Revert-Import-introspection-stub-machinery-too.patch"
