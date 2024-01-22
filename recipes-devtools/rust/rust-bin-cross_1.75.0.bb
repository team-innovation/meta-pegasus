
def get_by_triple(hashes, triple):
    try:
        return hashes[triple]
    except:
        raise bb.parse.SkipRecipe("Unsupported triple: %s" % triple)


def rust_std_md5(triple):
    HASHES = {
        "aarch64-unknown-linux-gnu": "284e33842c3dc608f0c78345e35e95de",
        "armv7-unknown-linux-gnueabihf": "77a79c85e62b7368a3192de38524596d",
        "x86_64-unknown-linux-gnu": "ae148f391e11cc8a02b8b0ed9999354e",
    }
    return get_by_triple(HASHES, triple)

def rust_std_sha256(triple):
    HASHES = {
        "aarch64-unknown-linux-gnu": "74960aa36e66541b3e9a8f78aa5df9c5c5a0e93207c0bb42a4fa141bccfbfd14",
        "armv7-unknown-linux-gnueabihf": "7b6d6b579a0c1d195f004f2f064169bdb80f57d029fc673d8564078b966cc22d",
        "x86_64-unknown-linux-gnu": "b7a43ed4bc9a9205b3ee2ece2a38232c8da5f1f14e7ed84fbefd492f9d474579",
    }
    return get_by_triple(HASHES, triple)

def rustc_md5(triple):
    HASHES = {
        "aarch64-unknown-linux-gnu": "094c3ca4879a9d15eb698c2aa4933535",
        "armv7-unknown-linux-gnueabihf": "9b8f920a6b6fdd0e979a08680ae95c26",
        "x86_64-unknown-linux-gnu": "f2a6069547e8b77643f470e49f2b698f",
    }
    return get_by_triple(HASHES, triple)

def rustc_sha256(triple):
    HASHES = {
        "aarch64-unknown-linux-gnu": "b7da2133a86e15a03e49307c0e91b0ab39c6ec8d0735a1c609499713f7e31571",
        "armv7-unknown-linux-gnueabihf": "5e5d9e153ad1b84dbaff6ccc9f6d2493e3f756f410ce914455724380484547f4",
        "x86_64-unknown-linux-gnu": "9684bc337f41110821fc94498e0596f76a061fae4667b195579b03fd141ad538",
    }
    return get_by_triple(HASHES, triple)

LIC_FILES_CHKSUM = "file://COPYRIGHT;md5=c2cccf560306876da3913d79062a54b9"

require rust-bin-cross.inc
