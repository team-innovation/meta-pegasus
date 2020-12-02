require python-avahi.inc

inherit python3-dir

BBCLASSEXTEND = "native"
NATIVE_INSTALL_WORKS = "1"
RDEPENDS_${PN} += "python3-core python3-dbus"
