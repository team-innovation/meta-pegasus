
def get_by_triple(hashes, triple):
    try:
        return hashes[triple]
    except:
        raise bb.parse.SkipRecipe("Unsupported triple: %s" % triple)


def rust_std_md5(triple):
    HASHES = {
        "aarch64-unknown-linux-gnu": "13a647b3b670f914259590cb180ccf7a",
        "armv7-unknown-linux-gnueabihf": "7a955d4a3d0c5e9e9ca509fb6aa78af1",
        "x86_64-unknown-linux-gnu": "336857bf48bc1fab0073893b272b56a3",
    }
    return get_by_triple(HASHES, triple)

def rust_std_sha256(triple):
    HASHES = {
        "aarch64-unknown-linux-gnu": "dd504733d3d8939b448ee93247d62d7fb09316b54c2f247b3c9f4709bf70784d",
        "armv7-unknown-linux-gnueabihf": "ae5ee9aee53e1248746aa24d993a85e38662c17777982e7cd8925e1953a699cc",
        "x86_64-unknown-linux-gnu": "89f6f6ef25e7e754940c54cc0584bfdb83e1df75019d5aa126e3fa66c2921b15",
    }
    return get_by_triple(HASHES, triple)

def rustc_md5(triple):
    HASHES = {
        "aarch64-unknown-linux-gnu": "f08ba1672fa18d9bc5b8b679dabb8023",
        "armv7-unknown-linux-gnueabihf": "7f141138729cec74eed15ad210969932",
        "x86_64-unknown-linux-gnu": "b2f3b7bb99e8220877f4080fed2534ba",
    }
    return get_by_triple(HASHES, triple)

def rustc_sha256(triple):
    HASHES = {
        "aarch64-unknown-linux-gnu": "3a2d1a5b5b713615162dcaa6319b6ad65ab4b951f90557dfb1e5c54f2a64c4ee",
        "armv7-unknown-linux-gnueabihf": "c73e4bf09478b4176d66f166b78ba5b08e87991b43f337ba07748cc86188bdb2",
        "x86_64-unknown-linux-gnu": "f414557b12f2a7a61fabf152b4b9d6cb436ff15698e64a3111bca1a94be97a3e",
    }
    return get_by_triple(HASHES, triple)

LIC_FILES_CHKSUM = "file://COPYRIGHT;md5=c2cccf560306876da3913d79062a54b9"

require rust-bin-cross.inc
