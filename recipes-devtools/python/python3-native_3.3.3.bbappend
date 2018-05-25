FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://enable-ctypes-cross-build-native.patch"

DEFAULT_PREFERENCE = "-1"
do_configure_append() {
	rm -f ${S}/Makefile.orig
	autoreconf --verbose --install --force --exclude=autopoint Modules/_ctypes/libffi || bbnote "_ctypes failed to autoreconf"
}

#do_install_append() {
#	ln -s -f python3.3m ${D}${includedir}/python3.3
#}

RPROVIDES += " \
	      python3-math-native \
	      python3-io-native \
	      python3-threading-native \
	      python3-subprocess-native \
	      "
