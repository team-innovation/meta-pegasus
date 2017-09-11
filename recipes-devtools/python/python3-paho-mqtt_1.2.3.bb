SUMMARY = "MQTT version 3.1/3.1.1 client library"
LICENSE = "EPL-1.0 | EDL-1.0"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=eb48c6ee2cb9f5b8b9fe75e6f817bdfc \
                    file://epl-v10;md5=8d383c379e91d20ba18a52c3e7d3a979 \
                    file://edl-v10;md5=c09f121939f063aeb5235972be8c722c \
"
SRCNAME = "paho-mqtt"
SRC_URI = "\
            https://files.pythonhosted.org/packages/source/p/${SRCNAME}/${SRCNAME}-${PV}.tar.gz \
            file://fixed_setup.patch \
           "

S = "${WORKDIR}/${SRCNAME}-${PV}"

inherit distutils3

SRC_URI[md5sum] = "4bd192ea24e7aa347f6d240101ef82f6"
SRC_URI[sha256sum] = "0fd746d8650563290f1bd0fec01e74cb57c3ab7406bdb58e5d9252bb5fa5ca44"

DEPENDS_${PN} = "\
        python3-distutils \
"

RDEPENDS_${PN} = "\
               python3-math \
               python3-io \
               python3-threading \
"
