SUMMARY = "Another JSON-Schema Validator"
HOMEPAGE = "https://github.com/pearmaster/jacobs-json-schema"
SECTION = "devel/python"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=4e408caf27642384f3ed2e8443a88974"

SRC_URI[sha256sum] = "12e6e88b59b80eef751339e11d2e289d6dd5620505f5abdb2964c78a5a459a6c"

inherit setuptools3 pypi

BBCLASSEXTEND = "native nativesdk"
