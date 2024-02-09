
def get_by_triple(hashes, triple):
    try:
        return hashes[triple]
    except:
        raise bb.parse.SkipRecipe("Unsupported triple: %s" % triple)


def rust_std_md5(triple):
    HASHES = {
        "aarch64-unknown-linux-gnu": "b3ddac44bd93afd8d8eeb43472838901",
        "armv7-unknown-linux-gnueabihf": "7855f963357eaa30dc02275bf9d8454c",
        "x86_64-unknown-linux-gnu": "b6650ab5179cbcb3423f5b900648d6c3",
    }
    return get_by_triple(HASHES, triple)

def rust_std_sha256(triple):
    HASHES = {
        "aarch64-unknown-linux-gnu": "f7d217f36f3dd167247f29c9bc0fc3939c69069666db915a908535d13397cd33",
        "armv7-unknown-linux-gnueabihf": "1c38aa0fc6e2091b40bbbe029f57173fbdf3d6ef0a511fbd36e4484cb58509a3",
        "x86_64-unknown-linux-gnu": "403e78b46d0730a21d6b25fe80ec947dc0ac4807c1f0930db68a4866552d839d",
    }
    return get_by_triple(HASHES, triple)

def rustc_md5(triple):
    HASHES = {
        "aarch64-unknown-linux-gnu": "aaa8ee8586ba70d709d8690a8953208d",
        "armv7-unknown-linux-gnueabihf": "b51d3188a974f200d9451cc023ba3a73",
        "x86_64-unknown-linux-gnu": "ff2c6b5ca91bf553def0e113faeb3042",
    }
    return get_by_triple(HASHES, triple)

def rustc_sha256(triple):
    HASHES = {
        "aarch64-unknown-linux-gnu": "20a9b0ac8551fbf1dabfe888887682598a337ab779feaa326d2e95aab9d1e5b8",
        "armv7-unknown-linux-gnueabihf": "a3b1f2d6c64b937f6d697d840c4ef9e660f65222e0b8aef3ecd4fbf87c247042",
        "x86_64-unknown-linux-gnu": "529f12c8874f4d912059b5b1d012f0d67bfaa89b7669509a6a2df8512da3f124",
    }
    return get_by_triple(HASHES, triple)

LIC_FILES_CHKSUM = "file://COPYRIGHT;md5=c2cccf560306876da3913d79062a54b9"

require rust-bin-cross.inc
