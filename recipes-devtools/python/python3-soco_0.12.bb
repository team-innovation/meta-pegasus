SUMMARY = "Python SoCo"
HOMEPAGE = " https://github.com/SoCo/SoCo"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE.rst;md5=07b0e2ca9ac77cd65cd4edf2e13367ea"

BBCLASSEXTEND = "native nativesdk"

SRC_URI += "file://clean-without-sphinx.patch"

SRC_URI[md5sum] = "85ee9c8d5ec63cb0bd0f85a974bfda4f"
SRC_URI[sha256sum] = "17fc9a6ce747b8fc1811e67450533c3393e63dd6ffe38cff703d9bfc276836f6"

inherit pypi setuptools3

RDEPENDS_${PN} = "\
  python3-xmltodict \
  python3-requests \
"
