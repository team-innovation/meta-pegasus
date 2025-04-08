SUMMARY = "Build and publish Rust crates as Python packages"
HOMEPAGE = "https://github.com/pyo3/maturin"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=fe2c8e59c924a2c49aaf113c3538d8c2"

SRC_URI = "https://files.pythonhosted.org/packages/source/m/maturin/maturin-${PV}.tar.gz"
SRC_URI[sha256sum] = "d01cb9013fd90c73b74db46465026e7c370b8b3c41bc9cccd0c9e8e61d9ef35d"
S = "${WORKDIR}/maturin-${PV}"

RDEPENDS:${PN} += "python3-setuptools python3-wheel"

inherit python_setuptools_build_meta cargo pypi