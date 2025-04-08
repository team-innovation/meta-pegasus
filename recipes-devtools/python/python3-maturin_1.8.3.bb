SUMMARY = "Build and publish Rust crates as Python packages"
HOMEPAGE = "https://github.com/pyo3/maturin"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5="

SRC_URI[sha256sum] = ""

RDEPENDS:${PN} += "python3-setuptools python3-wheel"

inherit python_setuptools_build_meta cargo pypi