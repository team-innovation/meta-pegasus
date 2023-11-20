
def get_by_triple(hashes, triple):
    try:
        return hashes[triple]
    except:
        raise bb.parse.SkipRecipe("Unsupported triple: %s" % triple)


def rust_std_md5(triple):
    HASHES = {
        "aarch64-unknown-linux-gnu": "4c248fdb43a2e721a621641b463cd343",
        "armv7-unknown-linux-gnueabihf": "31873569e00ad44d675ada7f0862d71f",
        "x86_64-unknown-linux-gnu": "4895aa6fdd0e5e1e705374ba919b0934",
    }
    return get_by_triple(HASHES, triple)

def rust_std_sha256(triple):
    HASHES = {
        "aarch64-unknown-linux-gnu": "56df7a51381bdf38ceba057c93581d00aab4619d78974bca9f47cbc49aa8497b",
        "armv7-unknown-linux-gnueabihf": "381f63d4571b7e1326cd62e0d4f2bca177192235cbfb05df63df13d3ab630ca5",
        "x86_64-unknown-linux-gnu": "798b3243d9236e4dc5d43f6b186333cd30c04926b2229568d1fc0f0eb432507f",
    }
    return get_by_triple(HASHES, triple)

def rustc_md5(triple):
    HASHES = {
        "aarch64-unknown-linux-gnu": "fda201c3364fe82017c5940c82e63459",
        "armv7-unknown-linux-gnueabihf": "0148d79b014fa67677ebec58cccf3139",
        "x86_64-unknown-linux-gnu": "552281c0e7c1af582a2292a88cc85c80",
    }
    return get_by_triple(HASHES, triple)

def rustc_sha256(triple):
    HASHES = {
        "aarch64-unknown-linux-gnu": "8e84e8065f21ea01ede5982869dd61160b1999b17f9a79911979ee936aea0de9",
        "armv7-unknown-linux-gnueabihf": "f55672d155e04c4ecb8187144da3e5ed323629f6f5c766c6841d713565fd8a17",
        "x86_64-unknown-linux-gnu": "358422396f3ff2a073f6fce66ca5aad9ae0596452711f6728c87698846c74e2a",
    }
    return get_by_triple(HASHES, triple)

LIC_FILES_CHKSUM = "file://COPYRIGHT;md5=c2cccf560306876da3913d79062a54b9"

require rust-bin-cross.inc
