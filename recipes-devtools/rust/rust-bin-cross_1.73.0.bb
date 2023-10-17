
def get_by_triple(hashes, triple):
    try:
        return hashes[triple]
    except:
        raise bb.parse.SkipRecipe("Unsupported triple: %s" % triple)


def rust_std_md5(triple):
    HASHES = {
        "aarch64-unknown-linux-gnu": "7bed58c1095730738aea79f45f3e557a",
        "armv7-unknown-linux-gnueabihf": "7422b99882d0bd1f09cb0d95abd14c9b",
        "x86_64-unknown-linux-gnu": "b687e1e8c08d4cf88065d271a75dc633",
    }
    return get_by_triple(HASHES, triple)

def rust_std_sha256(triple):
    HASHES = {
        "aarch64-unknown-linux-gnu": "47f2f904befca10a5f6dd68271a343b3700e651c67e25e723d4a4a0e2b4e445b",
        "armv7-unknown-linux-gnueabihf": "d6b645f127517c9553af2eeb919e120a59d84b2b035334a11a734f2ddc7775ca",
        "x86_64-unknown-linux-gnu": "9e941972c8679c2d852addf979455afd61e3ec33000cbc2421b162bcb05897a6",
    }
    return get_by_triple(HASHES, triple)

def rustc_md5(triple):
    HASHES = {
        "aarch64-unknown-linux-gnu": "2b2d0d20dcc5023f4c1d6030a893a7cb",
        "armv7-unknown-linux-gnueabihf": "21d4bd26fd8b50fd3c92d1b2a8b89a40",
        "x86_64-unknown-linux-gnu": "b177e99c118cce783f1916ed620db55e",
    }
    return get_by_triple(HASHES, triple)

def rustc_sha256(triple):
    HASHES = {
        "aarch64-unknown-linux-gnu": "5f7141617b833f84a279b19e7c349b95e839d924e2a3ed3ae545b2d4ab55ce05",
        "armv7-unknown-linux-gnueabihf": "31ba70b4cefeccf2a2300fc46fbfd3fc1c82239e483161da5c840f5130df9d65",
        "x86_64-unknown-linux-gnu": "31be7397a8a70fcb48e119925c9ff05554e2094140889ef9760b70a724d56346",
    }
    return get_by_triple(HASHES, triple)

LIC_FILES_CHKSUM = "file://COPYRIGHT;md5=c2cccf560306876da3913d79062a54b9"

require rust-bin-cross.inc
