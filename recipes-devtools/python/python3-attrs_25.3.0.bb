SUMMARY = "Classes Without Boilerplate"
HOMEPAGE = "https://github.com/python-attrs/attrs"
SECTION = "devel/python"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=5e55731824cf9205cfabeab9a0600887"

SRC_URI[sha256sum] = "75d7cefc7fb576747b2c81b4442d4d4a1ce0900973527c011d1030fd3bf4af1b"

DEPENDS = "\
    python3-hatchling-native \
    python3-hatch-vcs-native \
    python3-hatch-fancy-pypi-readme-native \
"

inherit python_setuptools_build_meta pypi

do_patch:append() {
    echo "***PATCHING ATTRS***"

    toml_file="${S}/pyproject.toml"
    if grep -q 'license-files = \["LICENSE"\]' "$toml_file"; then
        replacement=$(printf '[tool.hatch.metadata]\nlicense-files = { paths = ["LICENSE"] }')
        sed -i "s/license-files = \[\"LICENSE\"\]/$replacement/" "$toml_file"
    fi
}