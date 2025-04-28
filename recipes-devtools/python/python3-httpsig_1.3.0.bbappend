FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI += "\ 
    file://0002-Added-ed25519-signing-via-pysodium-as-optional-depen.patch \
"

RDEPENDS:${PN} = "${PYTHON_PN}-pysodium"