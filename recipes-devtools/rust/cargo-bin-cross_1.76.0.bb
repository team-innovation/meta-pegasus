
# Recipe for cargo 20240208
# This corresponds to rust release 1.76.0

def get_by_triple(hashes, triple):
    try:
        return hashes[triple]
    except:
        raise bb.parse.SkipRecipe("Unsupported triple: %s" % triple)

def cargo_md5(triple):
    HASHES = {
        "aarch64-unknown-linux-gnu": "b11da68ed8a864b5b96bfac94a6b758a",
        "armv7-unknown-linux-gnueabihf": "18dc7ff2f0dd7a222205aac7390fc76d",
        "x86_64-unknown-linux-gnu": "e574fee2ee4d22a65ba657abb4d6b506",
    }
    return get_by_triple(HASHES, triple)

def cargo_sha256(triple):
    HASHES = {
        "aarch64-unknown-linux-gnu": "0bc5824008fceb11afe19618d6484b0555353f656211956bd90eca8925e0023f",
        "armv7-unknown-linux-gnueabihf": "a9bcae087e0aba6fc1052d77840eaa5f9570d0015c68b1b23781d61e0a8fb0ee",
        "x86_64-unknown-linux-gnu": "7c8dd6f50fb85d0a9fa6e20dcf2ef5bf1f69b66bcfd220d1859716e1d0516163",
    }
    return get_by_triple(HASHES, triple)

def cargo_url(triple):
    URLS = {
        "aarch64-unknown-linux-gnu": "https://static.rust-lang.org/dist/2024-02-08/cargo-1.76.0-aarch64-unknown-linux-gnu.tar.gz",
        "armv7-unknown-linux-gnueabihf": "https://static.rust-lang.org/dist/2024-02-08/cargo-1.76.0-armv7-unknown-linux-gnueabihf.tar.gz",
        "x86_64-unknown-linux-gnu": "https://static.rust-lang.org/dist/2024-02-08/cargo-1.76.0-x86_64-unknown-linux-gnu.tar.gz",
    }
    return get_by_triple(URLS, triple)

DEPENDS += "rust-bin-cross-${TARGET_ARCH} (= 1.76.0)"

LIC_FILES_CHKSUM = "\
    file://LICENSE-APACHE;md5=71b224ca933f0676e26d5c2e2271331c \
    file://LICENSE-MIT;md5=b377b220f43d747efdec40d69fcaa69d \
"

require cargo-bin-cross.inc
