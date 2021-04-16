DESCRIPTION = "Read metadata from Python packages"
SECTION = "devel/python"
HOMEPAGE = "https://github.com/python/importlib_metadata"
LICENSE = "Apache"
LIC_FILES_CHKSUM = "file://LICENSE;md5=e88ae122f3925d8bde8319060f2ddb8e"

SRC_URI = "https://files.pythonhosted.org/packages/cb/bb/7a935a48bf751af244090a7bd558769942cf13a7eba874b8b25538f3db01/importlib_metadata-1.3.0.tar.gz"

S = "${WORKDIR}/importlib_metadata-1.3.0"

DEPENDS += "${PYTHON_PN}-setuptools-scm-git-archive-native"

SRC_URI[md5sum] = "a70c415c516d5f7802163ab8511011b1"
SRC_URI[sha256sum] = "073a852570f92da5f744a3472af1b61e28e9f78ccf0c9117658dc32b15de7b45"

inherit setuptools3

# Allows us to create a native package for staging in OE
BBCLASSEXTEND = "native nativesdk"
