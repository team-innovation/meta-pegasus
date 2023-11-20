
# Recipe for cargo 20231116
# This corresponds to rust release 1.74.0

def get_by_triple(hashes, triple):
    try:
        return hashes[triple]
    except:
        raise bb.parse.SkipRecipe("Unsupported triple: %s" % triple)

def cargo_md5(triple):
    HASHES = {
        "aarch64-unknown-linux-gnu": "1a8bfddc117af0695dcc7a43843b8282",
        "armv7-unknown-linux-gnueabihf": "b5a2cc100874ea5398d218d0aa380843",
        "x86_64-unknown-linux-gnu": "e60053511d763f771783f00a1896f10f",
    }
    return get_by_triple(HASHES, triple)

def cargo_sha256(triple):
    HASHES = {
        "aarch64-unknown-linux-gnu": "ab22b5aa6baa622a267f98ef2f1d06dd5a4a95b7ca6cadb0c431d31f1e018251",
        "armv7-unknown-linux-gnueabihf": "88127c7a75c931f6f2601a49693cddf76e3824ba41dc1a60039946cfdeda4702",
        "x86_64-unknown-linux-gnu": "38451abcf728c8583cba29dbd74debf56ce585dcc829ac7b03ccf94a563b8ddf",
    }
    return get_by_triple(HASHES, triple)

def cargo_url(triple):
    URLS = {
        "aarch64-unknown-linux-gnu": "https://static.rust-lang.org/dist/2023-11-16/cargo-1.74.0-aarch64-unknown-linux-gnu.tar.gz",
        "armv7-unknown-linux-gnueabihf": "https://static.rust-lang.org/dist/2023-11-16/cargo-1.74.0-armv7-unknown-linux-gnueabihf.tar.gz",
        "x86_64-unknown-linux-gnu": "https://static.rust-lang.org/dist/2023-11-16/cargo-1.74.0-x86_64-unknown-linux-gnu.tar.gz",
    }
    return get_by_triple(URLS, triple)

DEPENDS += "rust-bin-cross-${TARGET_ARCH} (= 1.74.0)"

LIC_FILES_CHKSUM = "\
    file://LICENSE-APACHE;md5=71b224ca933f0676e26d5c2e2271331c \
    file://LICENSE-MIT;md5=b377b220f43d747efdec40d69fcaa69d \
"

require cargo-bin-cross.inc
