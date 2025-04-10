SUMMARY = "The FLExible Network Tester"
HOMEPAGE = "https://github.com/tohojo/flent"
SECTION = "devel/python"

LICENSE = "GPL-3.0-or-later"
LIC_FILES_CHKSUM = "file://LICENSE;md5=adf3badb04547d9cfdbbd49478ae724f"

SRC_URI[sha256sum] = "04fc21de858863560423e79c822f405225f829afd8e5d62293099fbef341f9e8"

FILES.${PN} += "${datadir}/*"

inherit setuptools3 pypi