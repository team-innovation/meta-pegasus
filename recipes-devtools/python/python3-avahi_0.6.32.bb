require python-avahi.inc

inherit python3-dir

BBCLASSEXTEND = "native nativesdk"
RDEPENDS_${PN} += "python3-core python3-dbus"
