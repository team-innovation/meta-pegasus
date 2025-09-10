DESCRIPTION = "Package group used by Vivint for Pegasus thermostat" 
SUMMARY = "Vinint package group - pegasus"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

RDEPENDS:${PN} = " \
    mosquitto \
    mosquitto-clients \
    qtdeclarative \
    qtmultimedia \
"
