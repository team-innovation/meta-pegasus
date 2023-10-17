
# Recipe for cargo 20231005
# This corresponds to rust release 1.73.0

def get_by_triple(hashes, triple):
    try:
        return hashes[triple]
    except:
        raise bb.parse.SkipRecipe("Unsupported triple: %s" % triple)

def cargo_md5(triple):
    HASHES = {
        "aarch64-unknown-linux-gnu": "7c4283462f4057a20204680d72fa75a0",
        "armv7-unknown-linux-gnueabihf": "fe64e5a640f8ba84cb7e38fd886c43f0",
        "x86_64-unknown-linux-gnu": "fddc2f90143ef477136a80b261e83ff6",
    }
    return get_by_triple(HASHES, triple)

def cargo_sha256(triple):
    HASHES = {
        "aarch64-unknown-linux-gnu": "f0ef0b9e75613725357f526cd7ac259aac1da37927a8d919eff3eafb8f5087a7",
        "armv7-unknown-linux-gnueabihf": "3f4d3e00b72d35681c66158f5e2af85f07916c422dfce62c3d1bcd4c6245e8f1",
        "x86_64-unknown-linux-gnu": "78ad87102aebe101fb61d8fb6bb4b4da8674c57f0af810b3b3310f9f1a63d002",
    }
    return get_by_triple(HASHES, triple)

def cargo_url(triple):
    URLS = {
        "aarch64-unknown-linux-gnu": "https://static.rust-lang.org/dist/2023-10-05/cargo-1.73.0-aarch64-unknown-linux-gnu.tar.gz",
        "armv7-unknown-linux-gnueabihf": "https://static.rust-lang.org/dist/2023-10-05/cargo-1.73.0-armv7-unknown-linux-gnueabihf.tar.gz",
        "x86_64-unknown-linux-gnu": "https://static.rust-lang.org/dist/2023-10-05/cargo-1.73.0-x86_64-unknown-linux-gnu.tar.gz",
    }
    return get_by_triple(URLS, triple)

DEPENDS += "rust-bin-cross-${TARGET_ARCH} (= 1.73.0)"

LIC_FILES_CHKSUM = "\
    file://LICENSE-APACHE;md5=71b224ca933f0676e26d5c2e2271331c \
    file://LICENSE-MIT;md5=b377b220f43d747efdec40d69fcaa69d \
"

require cargo-bin-cross.inc
