LICENSE = "CLOSED"

PV = "1.0.0"
PR = "r3"

SRC_URI = "file://qt5.sh"

PACKAGES = "${PN}"
PACKAGE_ARCH = "${MACHINE_ARCH}"

S = "${WORKDIR}"

do_configure[noexec] = "1"

do_compile[noexec] = "1"

do_install() {
        install -d ${D}/etc/profile.d
        install -m 0755 ${WORKDIR}/qt5.sh ${D}/etc/profile.d
}

FILES_${PN} = "/etc/profile.d/*"

