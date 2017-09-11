SUMMARY = "MQTT version 3.1/3.1.1 client library"
LICENSE = "EPL-1.0 | EDL-1.0"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=eb48c6ee2cb9f5b8b9fe75e6f817bdfc \
                    file://epl-v10;md5=8d383c379e91d20ba18a52c3e7d3a979 \
                    file://edl-v10;md5=c09f121939f063aeb5235972be8c722c \
"
SRCNAME = "paho-mqtt"
SRC_URI = "\
            https://files.pythonhosted.org/packages/source/p/${SRCNAME}/${SRCNAME}-${PV}.tar.gz \
           "

S = "${WORKDIR}/${SRCNAME}-${PV}"

inherit distutils3

SRC_URI[md5sum] = "241150b3fcb920ddca4d33181f3238b1"
SRC_URI[sha256sum] = "9100a6aa706ab699d414ec02705a21eb66f436184691d0bf1f2a85a6213c6c1f"

DEPENDS_${PN} = "\
        python3-distutils \
"

RDEPENDS_${PN} = "\
               python3-math \
               python3-io \
               python3-threading \
"
