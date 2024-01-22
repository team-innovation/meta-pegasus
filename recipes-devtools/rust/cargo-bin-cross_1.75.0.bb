
# Recipe for cargo 20231228
# This corresponds to rust release 1.75.0

def get_by_triple(hashes, triple):
    try:
        return hashes[triple]
    except:
        raise bb.parse.SkipRecipe("Unsupported triple: %s" % triple)

def cargo_md5(triple):
    HASHES = {
        "aarch64-unknown-linux-gnu": "d8f4fafc8284184d4b82588a2cff97fa",
        "armv7-unknown-linux-gnueabihf": "9ba1183ecf9f0eef833491a47b22fb59",
        "x86_64-unknown-linux-gnu": "1308c9270e721b24b5a566b0cbf4af42",
    }
    return get_by_triple(HASHES, triple)

def cargo_sha256(triple):
    HASHES = {
        "aarch64-unknown-linux-gnu": "8734060ba397ce0306e6b70253551eb63af6982c19326fd734f60ca35814ad9b",
        "armv7-unknown-linux-gnueabihf": "c38d494d49c7a4242b0b92c954831697f5f55f8e6f545249bc1e879b418ab55f",
        "x86_64-unknown-linux-gnu": "ccd5f13a3101efadf09b1bbbebe8f099d97e99e1d4f0a29a37814a0dae429ede",
    }
    return get_by_triple(HASHES, triple)

def cargo_url(triple):
    URLS = {
        "aarch64-unknown-linux-gnu": "https://static.rust-lang.org/dist/2023-12-28/cargo-1.75.0-aarch64-unknown-linux-gnu.tar.gz",
        "armv7-unknown-linux-gnueabihf": "https://static.rust-lang.org/dist/2023-12-28/cargo-1.75.0-armv7-unknown-linux-gnueabihf.tar.gz",
        "x86_64-unknown-linux-gnu": "https://static.rust-lang.org/dist/2023-12-28/cargo-1.75.0-x86_64-unknown-linux-gnu.tar.gz",
    }
    return get_by_triple(URLS, triple)

DEPENDS += "rust-bin-cross-${TARGET_ARCH} (= 1.75.0)"

LIC_FILES_CHKSUM = "\
    file://LICENSE-APACHE;md5=71b224ca933f0676e26d5c2e2271331c \
    file://LICENSE-MIT;md5=b377b220f43d747efdec40d69fcaa69d \
"

require cargo-bin-cross.inc
